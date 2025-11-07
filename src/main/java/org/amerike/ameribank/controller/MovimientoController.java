package org.amerike.ameribank.controller;

import org.amerike.ameribank.dao.MovimientoDAO;
import org.amerike.ameribank.model.Movimiento;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    private final MovimientoDAO movimientoDAO;

    public MovimientoController(MovimientoDAO movimientoDAO) {
        this.movimientoDAO = movimientoDAO;
    }

    @PostMapping("/deposito")
    public Map<String, String> registrarDeposito(
            @RequestParam Long cuentaId,
            @RequestParam BigDecimal monto,
            @RequestParam String descripcion,
            @RequestParam String cuentaRemitente) {

        System.out.println("DEPOSITO - cuentaId: " + cuentaId + ", monto: " + monto);

        Map<String, String> response = new HashMap<>();
        try {
            movimientoDAO.registrarDeposito(cuentaId, monto, descripcion, cuentaRemitente);
            response.put("status", "success");
            response.put("message", "Depósito realizado exitosamente");
            System.out.println("DEPOSITO EXITOSO");
        } catch (Exception e) {
            System.out.println("ERROR DEPOSITO: " + e.getMessage());
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

        System.out.println("RETIRO - cuentaId: " + cuentaId + ", monto: " + monto);

        Map<String, String> response = new HashMap<>();
        try {
            movimientoDAO.registrarRetiro(cuentaId, monto, descripcion);
            response.put("status", "success");
            response.put("message", "Retiro realizado exitosamente");
            System.out.println("RETIRO EXITOSO");
        } catch (Exception e) {
            System.out.println("ERROR RETIRO: " + e.getMessage());
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

        System.out.println("TRANSFERENCIA - cuentaOrigen: " + cuentaOrigenId + ", cuentaDestino: " + cuentaDestino + ", monto: " + monto);

        Map<String, String> response = new HashMap<>();
        try {
            movimientoDAO.registrarTransferencia(cuentaOrigenId, cuentaDestino, monto, descripcion);
            response.put("status", "success");
            response.put("message", "Transferencia realizada exitosamente");
            System.out.println("TRANSFERENCIA EXITOSA");
        } catch (Exception e) {
            System.out.println("ERROR TRANSFERENCIA: " + e.getMessage());
            response.put("status", "error");
            response.put("message", "Error al realizar transferencia: " + e.getMessage());
        }
        return response;
    }

    @GetMapping("/consulta")
    public List<Movimiento> consultarMovimientos(
            @RequestParam Long cuentaId,
            @RequestParam(defaultValue = "10") int limite) {

        System.out.println("CONSULTA - cuentaId: " + cuentaId + ", limite: " + limite);

        try {
            List<Movimiento> movimientos = movimientoDAO.obtenerMovimientosPorCuenta(cuentaId, limite);
            System.out.println("MOVIMIENTOS ENCONTRADOS: " + movimientos.size());
            return movimientos;
        } catch (Exception e) {
            System.out.println("ERROR CONSULTA: " + e.getMessage());
            return List.of();
        }
    }
}