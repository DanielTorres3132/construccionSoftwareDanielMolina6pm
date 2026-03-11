package app.domain.models.Transfer.enums;

public enum TransferStatus { //estado de la tranferencia
    EXECUTED, //ejecutada
    WAITING_FOR_APPROVAL, //esperando por aprobacion
    REJECTED, //rechazada
    EXPIRED //expirada
}