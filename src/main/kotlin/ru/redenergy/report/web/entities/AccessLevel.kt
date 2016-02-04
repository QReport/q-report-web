package ru.redenergy.report.web.entities

/**
 * Represents user permission level
 */
enum class AccessLevel {
    /**
     * Can edit/create new users, view and modify tickets
     */
    MASTER,
    /**
     * Can view and modify tickets
     */
    HELPER,
    /**
     * Can only view tickets
     */
    GUEST;
}