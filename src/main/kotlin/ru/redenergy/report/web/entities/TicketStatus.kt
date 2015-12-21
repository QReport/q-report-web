package ru.redenergy.report.web.entities
/**
 * A copy of TicketStatus.class from QReport mod
 *
 * Represents status of ticket
 */
enum class TicketStatus(val translateKey: String) {

    /**
     * The ticket has just been received and have not been reviewed
     */
    OPEN("ticket.status.open"),
    /**
     * The ticket has been reviewed by moderator and currently in progress of clarification/fixing
     */
    IN_PROGRESS("ticket.status.inprogress"),
    /**
     * The ticket is closed. The problem has been fixed/bad player has been punished
     */
    CLOSED("ticket.status.closed");
}
