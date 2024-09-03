package pedro.datos;

import pedro.conexion.Conexion;
import pedro.dominio.Estudiante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAO {
    public List<Estudiante> listarEstudiantes() {
        List<Estudiante> estudiantes = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        Connection con = Conexion.getConexion();
        String sql = "SELECT * FROM estudiante ORDER BY id_estudiante";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                var estudiante = new Estudiante();
                estudiante.setIdEstudiante(rs.getInt("id_estudiante"));
                estudiante.setNombre(rs.getString("nombre"));
                estudiante.setApellido(rs.getString("apellido"));
                estudiante.setTelefono(rs.getString("telefono"));
                estudiante.setEmail(rs.getString("email"));
                estudiantes.add(estudiante);
            }
        } catch (Exception e) {
            System.out.println("Ocurrio un error al seleccionar datos " + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Ocurrio un error al cerrar la conexion " + e.getMessage());
            }
        }
        return estudiantes;
    }

    public boolean buscarEstudiantePorId(Estudiante estudiante) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = Conexion.getConexion();
        String sql = "SELECT * FROM estudiante WHERE id_estudiante = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, estudiante.getIdEstudiante());
            rs = ps.executeQuery();
            if (rs.next()) {
                estudiante.setNombre(rs.getString("nombre"));
                estudiante.setApellido(rs.getString("apellido"));
                estudiante.setTelefono(rs.getString("telefono"));
                estudiante.setEmail(rs.getString("email"));
                return true;
            }
        } catch (Exception e) {
            System.out.println("Ocurrio un error al buscar estudiante " + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Ocurrio un error al cerrar conexion " + e.getMessage());
            }
        }
        return false;
    }

    public boolean agregarEstudiante(Estudiante estudiante) {
        PreparedStatement ps;
        Connection con = Conexion.getConexion();
        String sql = "INSERT INTO estudiante(nombre, apellido, telefono, email)" +
                " VALUES(?, ?, ?, ?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, estudiante.getNombre());
            ;
            ps.setString(2, estudiante.getApellido());
            ;
            ps.setString(3, estudiante.getTelefono());
            ps.setString(4, estudiante.getEmail());
            ps.execute();
            return true;
        } catch (Exception e) {
            System.out.println("Ocurrio un error al agregar estudiante " + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Ocurrio un error al cerrar conexion " + e.getMessage());
            }
        }
        return false;
    }

    public boolean modificarEstudiante(Estudiante estudiante){
        PreparedStatement ps;
        Connection con = Conexion.getConexion();
        String sql = "UPDATE estudiante SET nombre=?, apellido=?, telefono=?, email=? " +
                " WHERE id_estudiante=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, estudiante.getNombre());
            ps.setString(2, estudiante.getApellido());
            ps.setString(3, estudiante.getTelefono());
            ps.setString(4, estudiante.getEmail());
            ps.setInt(5, estudiante.getIdEstudiante());
            ps.execute();
            return true;
        } catch (Exception e){
            System.out.println("Ocurrio un error al modificar estudiante " + e.getMessage());
        }
        finally {
            try {
                con.close();
            }catch (Exception e){
                System.out.println("Ocurrio un error al cerrar la conexion " + e.getMessage());
            }
        }
        return false;
    }

    public boolean eliminarEstudiante(Estudiante estudiante){
        PreparedStatement ps;
        Connection con = Conexion.getConexion();
        String sql = "DELETE FROM estudiante WHERE id_estudiante = ?";
        try{
        ps = con.prepareStatement(sql);
        ps.setInt(1, estudiante.getIdEstudiante());
        ps.execute();
        return true;
        } catch (Exception e){
            System.out.println("Error al eliminar estudiante " + e.getMessage());
        }
        finally {
            try {
                con.close();
            } catch (Exception e){
                System.out.println("Error al cerrar conexion " + e.getMessage());
            }
        }
        return false;
    }

    public static void main(String[] args) {
        var estudianteDao = new EstudianteDAO();

        // Agregar estudiante
//        var nuevoEstudiante = new Estudiante("Juan", "Gonzalez", "456789", "jgonzalez@mail.com");
//        var estudianteAgregado = estudianteDao.agregarEstudiante(nuevoEstudiante);
//        if (estudianteAgregado){
//            System.out.println("Estudiante agregado: " + nuevoEstudiante);
//        }else{
//            System.out.println("Error al agregar nuevo estudiante: " + nuevoEstudiante);
//        }

        // Modificar un estudiante existente
//        var estudianteModificar = new Estudiante(1, "Marcos", "Perez", "147258", "mperez@mail.com");
//        var estudianteModificado = estudianteDao.modificarEstudiante(estudianteModificar);
//        if (estudianteModificado){
//            System.out.println("Se modifico el estudiante " + estudianteModificar);
//        }else{
//            System.out.println("Error, no semodifico el estudiante " + estudianteModificar);
//        }

        // Eliminar estudiante
        var estudianteEliminar = new Estudiante(3);
        var estudianteEliminado = estudianteDao.eliminarEstudiante(estudianteEliminar);
        if (estudianteEliminado){
            System.out.println("Estudiante eliminado: " + estudianteEliminar);
        } else {
            System.out.println("Error, no se elimino el estudiante: " + estudianteEliminar);
        }

        // Listar estudiante
        System.out.println("Listado de estudiantes: ");
        List<Estudiante> estudiantes = estudianteDao.listarEstudiantes();
        estudiantes.forEach(System.out::println);

        // Buscar estudiante por Id
//        var estudiante1 = new Estudiante(3);
//        System.out.println("Estudiante antes de la busqueda " + estudiante1);
//        var estudianteEncontrado = estudianteDao.buscarEstudiantePorId(estudiante1);
//        if (estudianteEncontrado) {
//            System.out.println("Estudiante encontrado " + estudiante1);
//        } else {
//            System.out.println("No se encontro estudiante " + estudiante1.getIdEstudiante());
//        }
    }
}

