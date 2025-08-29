package BuenSabor.enums;

public enum Estado {
    pendiente, // previo a aceptacion por cocinero
    preparacion, // aceptado por cocinero
    cancelado, // cancelado por el cliente
    rechazado, // rechazado por cocinero
    delivery, // en proceso de entrega
    entregado // entregado al cliente por takeout o delivery
}
