package ru.redenergy.report.web.entities


/**
 * A copy of TicketReason.class from QReport mod
 *
 * Reasons of tickets
 */
enum class TicketReason(val translateKey: String) {

    /**
     * Some game error
     */
    BUG("ticket.reason.bug"),
    /**
     * Bad player behavior
     */
    GRIEFING("ticket.reason.grief"),
    /**
     * Suggestion of improvement
     */
    SUGGESTION("ticket.reason.suggest"),
    /**
     * Any other thing which don't fits into any of previous reasons
     */
    OTHER("ticket.reason.other");
}
