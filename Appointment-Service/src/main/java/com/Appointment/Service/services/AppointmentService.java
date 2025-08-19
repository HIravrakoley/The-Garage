package com.Appointment.Service.services;

import com.Appointment.Service.model.Appointment;
import com.Appointment.Service.repository.AppointmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepo appointmentRepo;

    public Appointment bookAppointment(Appointment appointment){
        return appointmentRepo.save(appointment);
    }
    public List<Appointment> getAppointment(String username){
        return appointmentRepo.findByUsername(username);
    }
    public List<Appointment> getAppointmentByGarageId(Long garageId){ return appointmentRepo.findByGarageId(garageId);}
    public List<Appointment> getAllAppointment(){
        return appointmentRepo.findAll();
    }
    public void deleteAppointment(Long id){
         appointmentRepo.deleteById(id);
    }

}
