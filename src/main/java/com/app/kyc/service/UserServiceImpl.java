package com.app.kyc.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.kyc.entity.Role;
import com.app.kyc.entity.User;
import com.app.kyc.model.ServiceProviderStatus;
import com.app.kyc.model.UserStatus;
import com.app.kyc.repository.UserRepository;
import com.app.kyc.request.ChangePasswordRequestDTO;
import com.app.kyc.request.ResetPasswordRequestDTO;
import com.app.kyc.service.common.ErrorCode;
import com.app.kyc.service.exception.InvalidDataException;
import com.app.kyc.service.storage.FileStorageService;
import com.app.kyc.util.EmailUtil;
import com.app.kyc.util.PaginationUtil;
import com.app.kyc.web.security.AuthRequest;
import com.app.kyc.web.security.AuthResponse;
import com.app.kyc.web.security.JWTUtil;
import com.app.kyc.web.security.SecurityHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Service
public class UserServiceImpl implements UserService
{

   @Autowired
   private EntityManager entityManager;

   @Autowired
   private UserRepository userRepository;

   @Autowired
   AuthenticationManager authenticationManager;

   @Autowired
   JWTUtil jwtUtil;

   @Autowired
   SecurityHelper securityHelper;

   @Autowired
   Environment environment;

   @Autowired
   FileStorageService storageService;

   public User getUserById(Long id)
   {
      return userRepository.findById(id).get();
   }

   public User getUserByEmail(String email)
   {
      return userRepository.findByEmail(email);
   }

   public Map<String, Object> getAllUsers(String params) throws JsonMappingException, JsonProcessingException
   {
      Page<User> pageUser = userRepository.findAll(PaginationUtil.getPageable(params));
      Map<String, Object> usersWithCount = new HashMap<String, Object>();
      usersWithCount.put("data", pageUser.toList());
      usersWithCount.put("count", pageUser.getTotalElements());
      return usersWithCount;
   }

   public int getTotalUsers()
   {
      return userRepository.findAll().size();
   }

   public void addUser(User user, MultipartFile file, Long currentUser) throws InvalidDataException
   {
      User originalUser = getUserByEmail(user.getEmail());
      if(originalUser == null)
      {
         storageService.save(file,
            FilenameUtils.removeExtension(file.getOriginalFilename()) + Instant.now().toEpochMilli() + "." + FilenameUtils.getExtension(file.getOriginalFilename()));
         user.setGovernmentId(file.getOriginalFilename());
         user.setCreatedOn(new Date());
         user.setStatus(UserStatus.Inactive);
         user.setCreatedBy(currentUser);
         Random rand = new Random();
         int random_code = rand.ints(1000, 9999).findFirst().getAsInt();
         user.setCode(Long.valueOf(random_code));
         Date nowDate = new Date();
         LocalDateTime localDateTime = nowDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
         localDateTime = localDateTime.plusDays(1);
         user.setCodeExpiry(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
         userRepository.save(user);
         String message = "Welcome to the National KYC platform. Please click on the link below to set up your new account: " + environment
            .getProperty("reactBaseUrl") + "/verifyUser?email=" + user.getEmail() + "&code=" + Long.valueOf(random_code) + "\r\n\r\nBest,\r\n" + "National KYC";
         EmailUtil.sendEmail(user.getEmail(), "New Account - National KYC Platform", message);
      }
      else
      {
         throw new InvalidDataException(ErrorCode.EMAIL_TAKEN);
      }
   }

   public User updateUser(User user) throws NotFoundException
   {
      User originalUser = getUserByEmail(user.getEmail());
      if(originalUser == null)
      {
         throw new NotFoundException();
      }
      else
      {
         originalUser.setDepartment(user.getDepartment());
         originalUser.setFirstName(user.getFirstName());
         originalUser.setLastName(user.getLastName());
         originalUser.setPhone(user.getPhone());
         originalUser.setIndustry(user.getIndustry());
         originalUser.setRole(user.getRole());
         if(user.getServiceProvider() != null) {
            originalUser.setServiceProvider(user.getServiceProvider());
         }
         return userRepository.save(originalUser);
      }
   }

   public void deleteUser(Long id)
   {
      userRepository.deleteById(id);
   }

   public User updateLastLogin(String email)
   {
      User user = getUserByEmail(email);
      user.setLastLogin(new Date());
      return userRepository.save(user);
   }

   public User verifyUser(String email, Long code)
   {
      CriteriaBuilder cb = entityManager.getCriteriaBuilder();
      CriteriaQuery<User> query = cb.createQuery(User.class);

      List<Predicate> predicates = new ArrayList<>();

      Root<User> root = query.from(User.class);

      Predicate emailPredicate = cb.equal(root.get("Email"), email);
      predicates.add(emailPredicate);

      Predicate codePredicate = cb.equal(root.get("Code"), code);
      predicates.add(codePredicate);

      Predicate expiryPredicate = cb.equal(root.get("CodeExpiry"), new Date());
      predicates.add(expiryPredicate);

      query.select(root).where(predicates.toArray(new Predicate[predicates.size()]));
      TypedQuery<User> typedQuery = entityManager.createQuery(query);

      List<User> users = typedQuery.getResultList();
      if(users.size() == 1)
      {
         entityManager.close();
         return users.get(0);
      }
      return null;
   }

   public User deactivateUser(Long id)
   {
      User user = getUserById(id);
      user.setStatus(UserStatus.Inactive);
      return userRepository.save(user);
   }

   public User activateUser(Long id)
   {
      User user = getUserById(id);
      user.setStatus(UserStatus.Active);
      return userRepository.save(user);
   }

   @Override
   public Role getUserRoleById(Long id)
   {
      User user = getUserById(id);
      return user.getRole();
   }

   @Override
   public AuthResponse authenticateUser(AuthRequest authRequest) throws InvalidDataException
   {
      User user = getUserByEmail(authRequest.getEmail());
      if(user == null) throw new InvalidDataException(ErrorCode.EMAIL_NOT_FOUND);
      if(!(securityHelper.passwordIsValid(authRequest.getPassword(), user.getPassword())))
      {
         throw new InvalidDataException(ErrorCode.PASSWORD_INCORRECT);
      }
      if(user.getStatus() != UserStatus.Active)
      {
         throw new InvalidDataException(ErrorCode.USER_INACTIVE);
      }
      else
         if(user.getServiceProvider() != null && user.getServiceProvider().getStatus() != ServiceProviderStatus.Active)
         {
            throw new InvalidDataException(ErrorCode.SERVICE_PROVIDER_INACTIVE);
         }
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
      updateLastLogin(authRequest.getEmail());
      AuthResponse authResponse = new AuthResponse(jwtUtil.generateToken(authRequest.getEmail(), user.getRole()), user.getRole().getName(), user.getId(), user.getFirstName());

      return authResponse;
   }

   @Override
   public void verifyEmailToChangePassword(String email)
   {
      User user = userRepository.findByEmail(email);
      if(user != null)
      {
         Random rand = new Random();
         int random_code = rand.ints(1000, 9999).findFirst().getAsInt();
         user.setCode(Long.valueOf(random_code));
         Date nowDate = new Date();
         LocalDateTime localDateTime = nowDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
         localDateTime = localDateTime.plusDays(1);
         user.setCodeExpiry(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
         userRepository.save(user);
         String message = "It seems you are trying to reset your password. Please click on the link below to reset your password: " + environment
            .getProperty("reactBaseUrl") + "/resetPassword?email=" + user.getEmail() + "&code=" + Long.valueOf(random_code) + "\r\n\r\nBest,\r\n" + "National KYC";
         EmailUtil.sendEmail(user.getEmail(), "Password Reset - National KYC Platform", message);
      }
   }

   public User changePassword(ChangePasswordRequestDTO changePasswordRequestDTO) throws InvalidDataException
   {
      User user = getUserByEmail(changePasswordRequestDTO.getEmail());
      if((user == null)) throw new InvalidDataException(ErrorCode.EMAIL_NOT_FOUND);
      if(!(securityHelper.passwordIsValid(changePasswordRequestDTO.getOldPassword(), user.getPassword())))
      {
         throw new InvalidDataException(ErrorCode.PASSWORD_INCORRECT);
      }
      BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      user.setPassword(passwordEncoder.encode(changePasswordRequestDTO.getNewPassword()));
      return userRepository.save(user);
   }

   @Override
   public User resetPassword(ResetPasswordRequestDTO resetPasswordRequestDTO) throws InvalidDataException, NotFoundException
   {
      User user = getUserByEmail(resetPasswordRequestDTO.getEmail());
      if((user == null)) throw new NotFoundException();
      if(!resetPasswordRequestDTO.getCode().equals(user.getCode()) || user.getCodeExpiry().before(new Date()))
      {
         throw new InvalidDataException(ErrorCode.INVALID_CODE);
      }
      BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      user.setPassword(passwordEncoder.encode(resetPasswordRequestDTO.getNewPassword()));
      return userRepository.save(user);
   }

   @Override
   public void setupUserPassword(ResetPasswordRequestDTO resetPasswordRequestDTO) throws InvalidDataException, NotFoundException
   {
      User user = getUserByEmail(resetPasswordRequestDTO.getEmail());
      if((user == null))
      {
         throw new NotFoundException();
      }
      if(!resetPasswordRequestDTO.getCode().equals(user.getCode()) || user.getCodeExpiry().before(new Date()))
      {
         throw new InvalidDataException(ErrorCode.INVALID_CODE);
      }
      BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      user.setPassword(passwordEncoder.encode(resetPasswordRequestDTO.getNewPassword()));
      user.setStatus(UserStatus.Active);
      userRepository.save(user);
   }

   @Override
   public Map<String, Object> getAllByCreatedById(String params, Long createdById) throws JsonMappingException, JsonProcessingException
   {
      Page<User> pageUser = userRepository.findAllByCreatedBy(PaginationUtil.getPageable(params), createdById);
      Map<String, Object> usersWithCount = new HashMap<String, Object>();
      usersWithCount.put("data", pageUser.toList());
      usersWithCount.put("count", pageUser.getTotalElements());
      return usersWithCount;
   }

   @Override
   public List<User> getByServiceProviderId(Long serviceProviderId)
   {
      return userRepository.findAllByServiceProviderId(serviceProviderId);
   }

}
