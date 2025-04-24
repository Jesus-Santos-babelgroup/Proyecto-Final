# Client Controller

package com.helloworld.renting.controller;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    public ClientController() {
    }

    @PostMapping("")
    public ResponseEntity<ClientDto> createClient(@Valid @RequestBody ClientDto clientDto) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> updateClient(@PathVariable Long id,
                                                  @Valid @RequestBody ClientDto clientDto) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getClient(@PathVariable Long id) {
        return null;
    }

    @GetMapping("")
    public ResponseEntity<List<ClientDto>> listClients() {
        return null;
    }

}

# Request Controller

package com.helloworld.renting.controller;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    public RequestController() {
    }

    @PostMapping("")
    public ResponseEntity<RequestSummaryDto> createRequest(@Valid @RequestBody RequestDto requestDto) {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RequestDetailDto> getRequest(@PathVariable Long id) {
        return null;
    }

    @GetMapping("")
    public ResponseEntity<List<RequestSummaryDto>> listRequests() {
        return null;
    }

}

# Vehicle Controller

package com.helloworld.renting.controller;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    public VehicleController() {
    }

    @GetMapping("")
    public ResponseEntity<List<VehicleDto>> listVehicles() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleDto> getVehicle(@PathVariable Long id) {
        return null;
    }

}


