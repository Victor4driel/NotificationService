package com.vabp.notification.listener;

import com.vabp.notification.constant.ConstantMessage;
import com.vabp.notification.domain.Proposal;
import com.vabp.notification.service.NotificationSNSService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PendingProposalListener {

    private NotificationSNSService notificationSNSService;

    @RabbitListener(queues = "${rabbit.queue.pending.proposal}")
    public void pendingProposal(Proposal proposal) {
        String message = String.format(ConstantMessage.PROPOSAL_IN_ANALYSIS, proposal.getUsuario().getNome());
        notificationSNSService.notify(proposal.getUsuario().getTelefone(), message);
    }
}
