package org.amerike.ameribank.dao;

import org.amerike.ameribank.config.ConexionDB;
import org.amerike.ameribank.model.Movimiento;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MovimientoDAO {

    public void registrarDeposito(Long cuentaId, BigDecimal monto, String descripcion, String cuentaRemitente) throws Exception {
        String sql = "CALL RegistrarDeposito(?, ?, ?, ?)";

        System.out.println("DAO - RegistrarDeposito:");
        System.out.println("  cuentaId: " + cuentaId);
        System.out.println("  monto: " + monto);
        System.out.println("  descripcion: " + descripcion);
        System.out.println("  cuentaRemitente: " + cuentaRemitente);

        try (Connection conn = ConexionDB.conectar();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setLong(1, cuentaId);
            stmt.setBigDecimal(2, monto);
            stmt.setString(3, descripcion);
            stmt.setString(4, cuentaRemitente);

            stmt.executeUpdate();
            System.out.println("DAO - Deposito ejecutado exitosamente");

        } catch (SQLException e) {
            System.out.println("DAO - ERROR SQL en deposito: " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
            throw new Exception("Error al registrar depósito: " + e.getMessage(), e);
        } catch (Exception e) {
            System.out.println("DAO - ERROR general en deposito: " + e.getMessage());
            throw e;
        }
    }

    public void registrarRetiro(Long cuentaId, BigDecimal monto, String descripcion) throws Exception {
        String sql = "CALL RegistrarRetiro(?, ?, ?)";

        System.out.println("DAO - RegistrarRetiro:");
        System.out.println("  cuentaId: " + cuentaId);
        System.out.println("  monto: " + monto);
        System.out.println("  descripcion: " + descripcion);

        try (Connection conn = ConexionDB.conectar();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setLong(1, cuentaId);
            stmt.setBigDecimal(2, monto);
            stmt.setString(3, descripcion);

            stmt.executeUpdate();
            System.out.println("DAO - Retiro ejecutado exitosamente");

        } catch (SQLException e) {
            System.out.println("DAO - ERROR SQL en retiro: " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
            throw new Exception("Error al registrar retiro: " + e.getMessage(), e);
        } catch (Exception e) {
            System.out.println("DAO - ERROR general en retiro: " + e.getMessage());
            throw e;
        }
    }

    public void registrarTransferencia(Long cuentaOrigenId, String cuentaDestino, BigDecimal monto, String descripcion) throws Exception {
        String sql = "CALL RegistrarTransferencia(?, ?, ?, ?)";

        System.out.println("DAO - RegistrarTransferencia:");
        System.out.println("  cuentaOrigenId: " + cuentaOrigenId);
        System.out.println("  cuentaDestino: " + cuentaDestino);
        System.out.println("  monto: " + monto);
        System.out.println("  descripcion: " + descripcion);

        try (Connection conn = ConexionDB.conectar();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setLong(1, cuentaOrigenId);
            stmt.setString(2, cuentaDestino);
            stmt.setBigDecimal(3, monto);
            stmt.setString(4, descripcion);

            stmt.executeUpdate();
            System.out.println("DAO - Transferencia ejecutada exitosamente");

        } catch (SQLException e) {
            System.out.println("DAO - ERROR SQL en transferencia: " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
            throw new Exception("Error al registrar transferencia: " + e.getMessage(), e);
        } catch (Exception e) {
            System.out.println("DAO - ERROR general en transferencia: " + e.getMessage());
            throw e;
        }
    }

    public List<Movimiento> obtenerMovimientosPorCuenta(Long cuentaId, int limite) throws Exception {
        String sql = "CALL ObtenerMovimientosPorCuenta(?, ?)";
        List<Movimiento> movimientos = new ArrayList<>();

        System.out.println("DAO - ObtenerMovimientosPorCuenta:");
        System.out.println("  cuentaId: " + cuentaId);
        System.out.println("  limite: " + limite);

        try (Connection conn = ConexionDB.conectar();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setLong(1, cuentaId);
            stmt.setInt(2, limite);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Movimiento movimiento = new Movimiento();
                movimiento.setId(rs.getLong("id"));
                movimiento.setCuentaId(rs.getLong("cuenta_id"));
                movimiento.setTipoMovimiento(rs.getString("tipo_movimiento"));
                movimiento.setMonto(rs.getBigDecimal("monto"));
                movimiento.setDescripcion(rs.getString("descripcion"));
                movimiento.setFechaMovimiento(rs.getTimestamp("fecha_movimiento").toLocalDateTime());
                movimiento.setCuentaRemitente(rs.getString("cuenta_remitente"));
                movimiento.setCuentaReceptora(rs.getString("cuenta_receptora"));

                movimientos.add(movimiento);
            }

            System.out.println("DAO - Movimientos encontrados: " + movimientos.size());

        } catch (SQLException e) {
            System.out.println("DAO - ERROR SQL en consulta: " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
            throw new Exception("Error al consultar movimientos: " + e.getMessage(), e);
        }

        return movimientos;
    }
}