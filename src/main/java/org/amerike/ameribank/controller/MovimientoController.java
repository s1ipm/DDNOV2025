package org.amerike.ameribank.controller;

import org.amerike.ameribank.service.MovimientoService;
import org.amerike.ameribank.model.Movimiento;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    private final MovimientoService movimientoService;

    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @PostMapping("/deposito")
    public Map<String, String> registrarDeposito(
            @RequestParam Long cuentaId,
            @RequestParam BigDecimal monto,
            @RequestParam String descripcion,
            @RequestParam String cuentaRemitente) {

        System.out.println("CONTROLLER - DEPOSITO INICIADO");
        System.out.println("  cuentaId: " + cuentaId);
        System.out.println("  monto: " + monto);
        System.out.println("  descripcion: " + descripcion);
        System.out.println("  cuentaRemitente: " + cuentaRemitente);

        Map<String, String> response = new HashMap<>();
        try {
            movimientoService.realizarDeposito(cuentaId, monto, descripcion, cuentaRemitente);
            response.put("status", "success");
            response.put("message", "Depósito realizado exitosamente");
            System.out.println("CONTROLLER - DEPOSITO EXITOSO");
        } catch (Exception e) {
            System.out.println("CONTROLLER - ERROR DEPOSITO: " + e.getMessage());
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Error al realizar depósito: " + e.getMessage());
        }
        return response;
    }

    @PostMapping("/retiro")
    public Map<String, String> registrarRetiro(
            @RequestParam Long cuentaId,
            @RequestParam BigDecimal monto,
            @RequestParam String descripcion) {

        System.out.println("CONTROLLER - RETIRO INICIADO");
        System.out.println("  cuentaId: " + cuentaId);
        System.out.println("  monto: " + monto);
        System.out.println("  descripcion: " + descripcion);

        Map<String, String> response = new HashMap<>();
        try {
            movimientoService.realizarRetiro(cuentaId, monto, descripcion);
            response.put("status", "success");
            response.put("message", "Retiro realizado exitosamente");
            System.out.println("CONTROLLER - RETIRO EXITOSO");
        } catch (Exception e) {
            System.out.println("CONTROLLER - ERROR RETIRO: " + e.getMessage());
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Error al realizar retiro: " + e.getMessage());
        }
        return response;
    }

    @PostMapping("/transferencia")
    public Map<String, String> registrarTransferencia(
            @RequestParam Long cuentaOrigenId,
            @RequestParam String cuentaDestino,
            @RequestParam BigDecimal monto,
            @RequestParam String descripcion) {

        System.out.println("CONTROLLER - TRANSFERENCIA INICIADA");
        System.out.println("  cuentaOrigenId: " + cuentaOrigenId);
        System.out.println("  cuentaDestino: " + cuentaDestino);
        System.out.println("  monto: " + monto);
        System.out.println("  descripcion: " + descripcion);

        Map<String, String> response = new HashMap<>();
        try {
            movimientoService.realizarTransferencia(cuentaOrigenId, cuentaDestino, monto, descripcion);
            response.put("status", "success");
            response.put("message", "Transferencia realizada exitosamente");
            System.out.println("CONTROLLER - TRANSFERENCIA EXITOSA");
        } catch (Exception e) {
            System.out.println("CONTROLLER - ERROR TRANSFERENCIA: " + e.getMessage());
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Error al realizar transferencia: " + e.getMessage());
        }
        return response;
    }

    @GetMapping("/consulta")
    public List<Movimiento> consultarMovimientos(
            @RequestParam Long cuentaId,
            @RequestParam(defaultValue = "10") int limite) {

        System.out.println("CONTROLLER - CONSULTA INICIADA");
        System.out.println("  cuentaId: " + cuentaId);
        System.out.println("  limite: " + limite);

        try {
            List<Movimiento> movimientos = movimientoService.obtenerMovimientosPorCuenta(cuentaId, limite);
            System.out.println("CONTROLLER - MOVIMIENTOS ENCONTRADOS: " + movimientos.size());
            return movimientos;
        } catch (Exception e) {
            System.out.println("CONTROLLER - ERROR CONSULTA: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }
}