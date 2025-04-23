# Arquitectura Provisional del Backend de Renting de Vehículos

Todas las carpetas y clases mostradas a continuación son **provisionales** y servirán como punto de partida.
Pueden reorganizarse, renombrarse o dividirse en módulos más fines según avance el desarrollo.
De hecho lo ideal es que vayan cambiando según las necesidades de cada feature y que cada persona ajuste esta
arquitectura como crea conveniente.
Todas las clases creadas actualmente están vacías y son solo un esqueleto para que se pueda subir la estructura de
carpetas al repositorio.
Los DTO y Modelos deberán ser generados a partir de los requisitos de la API y de la base de datos y lo que hay
actualmente son ejemplos.
La estructura de extras se ha ideado siguiendo un patrón estrategia y habría que tener en cuenta la posibilidad
de separar en otro servicio aparte el manejo de las cuotas para el cálculo total.
En approval se tiene el servicio, el evaluador de reglas (interfaz) y las reglas de aprobación y denegación. También se
tiene
de momento una clase abstracta para las reglas de aprobación y otra para las de denegación. De nuevo, esto está sujeto a
cambios.

---

## Estructura de paquetes

```text
com.helloworld.renting
├── controller/             # Controladores REST
│   ├── ClientController.java
│   ├── VehicleController.java
│   └── RequestController.java
│
├── dto/                    # Data Transfer Objects (contrato API)
│   ├── ClientDto.java
│   ├── VehicleDto.java
│   ├── RequestDto.java
│   ├── RequestSummaryDto.java
│   └── RequestDetailDto.java
│
├── entities/                  # Entidades de dominio (@Entity JPA)
│   ├── Client.java
│   ├── Vehicle.java
│   ├── Request.java
│   ├── RequestItem.java
│   ├── Extra.java
│   └── Guarantee.java
│
├── exceptions/                  # Excepciones personalizadas
│   ├── attributes/
│   ├── db/
│   ├── notfound/
│   └── RentingException.java
│
├── repository/             # Repositorios JPA
│   ├── ClientRepository.java
│   ├── VehicleRepository.java
│   └── RequestRepository.java
│
├── service/                # Lógica de negocio y orquestación
│   ├── client/
│   │   └── ClientService.java
│   │
│   ├── vehicle/
│   │   └── VehicleService.java
│   │
│   └── request/
│       ├── RequestService.java
│       │
│       ├── extras/         # Cálculo de costes de extras (Strategy)
│       │   ├── ExtraCalculator.java
│       │   ├── FixedAmountExtraCalculator.java
│       │   ├── PercentageExtraCalculator.java
│       │   └── ExtraCalculatorFactory.java
│       │
│       └── approval/       # Motor de reglas (Specification / Chain)
│           ├── ApprovalService.java
│           ├── RuleEvaluator.java
│           └── rules/
│               ├── approval/
│               │   ├── ApprovedRule.java
│               │   ├── DebtLessThanMonthlyQuotaRule.java
│               │   ├── NotInInternalDebtsRule.java
│               │   └── (OtrasApprovalRules).java
│               │
│               └── denial/
│                   ├── DenialRule.java
│                   └── (OtrasDenialRules).java
resources/
```

