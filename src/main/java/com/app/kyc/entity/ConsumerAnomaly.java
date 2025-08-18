package com.app.kyc.entity;

import javax.persistence.*;

@Entity
@Table(name = "consumers_anomalies")
public class ConsumerAnomaly {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String notes;

    @ManyToOne
    private Anomaly anomaly;

    @ManyToOne
    private Consumer consumer;

    public Anomaly getAnomaly(){
        return anomaly;
    }

    public void setAnomaly(Anomaly anomaly)
    {
        this.anomaly = anomaly;
    }

    public Consumer getConsumer(){
        return consumer;
    }
    
    public void setConsumer(Consumer consumer)
    {
        this.consumer = consumer;
    }
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
