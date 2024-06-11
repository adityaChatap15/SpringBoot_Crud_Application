package com.example.Flight_Service1.service;

import com.example.Flight_Service1.dto.FlightBookingAcknowledgement;
import com.example.Flight_Service1.dto.FlightBookingRequest;
import com.example.Flight_Service1.entity.PassengerInfo;
import com.example.Flight_Service1.entity.PaymentInfo;
import com.example.Flight_Service1.repository.PassengerInfoRepository;
import com.example.Flight_Service1.repository.PaymentInfoRepository;
import com.example.Flight_Service1.utils.PaymentUtils;

import java.util.UUID;

public class FlightBookingService {
    //    @Autowired

    private PassengerInfoRepository passengerInfoRepository;

    //    @Autowired
    private PaymentInfoRepository paymentInfoRepository;

    public FlightBookingAcknowledgement bookFlightTicket(FlightBookingRequest request) {
        FlightBookingAcknowledgement acknowledgement = null;

        PassengerInfo passengerInfo = request.getPassengerInfo();
        passengerInfo = passengerInfoRepository.save(passengerInfo);

        PaymentInfo paymentInfo = request.getPaymentInfo();

        PaymentUtils.validateCreditLimit(paymentInfo.getAccountNo(), passengerInfo.getFare());
        paymentInfo.setPassengerId(passengerInfo.getPId());
        paymentInfo.setAmount(passengerInfo.getFare());
        paymentInfoRepository.save(paymentInfo);

        return new FlightBookingAcknowledgement("SUCCESS", passengerInfo.getFare(), UUID.randomUUID().toString().split("-")[0], passengerInfo);
    }
}