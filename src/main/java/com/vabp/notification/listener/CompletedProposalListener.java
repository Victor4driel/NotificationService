package com.vabp.notification.listener;

import com.vabp.notification.constant.ConstantMessage;
import com.vabp.notification.domain.Proposal;
import com.vabp.notification.service.NotificationSNSService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompletedProposalListener {

    private NotificationSNSService notificationSNSService;

    public CompletedProposalListener(NotificationSNSService notificationSNSService) {
        this.notificationSNSService = notificationSNSService;
    }

    @RabbitListener(queues = "${rabbit.queue.completed.proposal}")
    public void completedProposal(Proposal proposal) {
        String proposalMessage = proposal.getAprovada()
                ? ConstantMessage.PROPOSAL_APPROVED
                : ConstantMessage.PROPOSAL_REJECTED;

        String messageFormatted = String.format(
                proposalMessage, proposal.getUsuario().getNome()
        );

        notificationSNSService.notify(
                proposal.getUsuario().getTelefone(), messageFormatted
        );
    }
}
