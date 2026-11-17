package com.ayush.rateLimiterApp.invitationManagement.controller;

import com.ayush.rateLimiterApp.invitationManagement.dto.InvitationRequestDto;
import com.ayush.rateLimiterApp.invitationManagement.dto.InvitationResponseDto;
import com.ayush.rateLimiterApp.invitationManagement.service.InvitationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/invite-user")
public class InvitationController {

    private InvitationService invitationService;

    public InvitationController(InvitationService invitationService){
        this.invitationService = invitationService;
    }

    public ResponseEntity<InvitationResponseDto> inviteUser(@RequestBody InvitationRequestDto invitationRequestDto
                                                            ){


        return null;
    }
}
