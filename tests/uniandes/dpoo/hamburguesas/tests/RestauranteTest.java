package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.excepciones.IngredienteRepetidoException;
import uniandes.dpoo.hamburguesas.excepciones.NoHayPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.excepciones.ProductoFaltanteException;
import uniandes.dpoo.hamburguesas.excepciones.ProductoRepetidoException;
import uniandes.dpoo.hamburguesas.excepciones.YaHayUnPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;
import uniandes.dpoo.hamburguesas.mundo.Restaurante;

public class RestauranteTest
{
	///atributis
    private Restaurante restaurante;
    private File archivoIngredientes;
    private File archivoMenu;
    private File archivoCombos;

    @BeforeEach
    void setUp( ) throws Exception
    {
        restaurante = new Restaurante( );
        archivoIngredientes = new File( "./data/ingredientes.txt" );
        archivoMenu = new File( "./data/menu.txt" );
        archivoCombos = new File( "./data/combos.txt" );
    }

    @AfterEach
    void tearDown( ) throws Exception
    {
        restaurante = null;
    }

    @Test
    void testCargarInformacionRestaurante( ) throws Exception
    {
        restaurante.cargarInformacionRestaurante( archivoIngredientes, archivoMenu, archivoCombos );

        assertEquals( 15, restaurante.getIngredientes( ).size( ), "La cantidad de ingredientes cargados no es la esperada." );
        assertEquals( 22, restaurante.getMenuBase( ).size( ), "La cantidad de productos del menú no es la esperada." );
        assertEquals( 4, restaurante.getMenuCombos( ).size( ), "La cantidad de combos no es la esperada." );
    }

    @Test
    void testCargarIngredientesRepetidos( )
    {
        File repetidos = new File( "./data/ingredientes_repetidos.txt" );
        assertThrows( IngredienteRepetidoException.class, ( ) -> {
            restaurante.cargarInformacionRestaurante( repetidos, archivoMenu, archivoCombos );
        } );
    }

    @Test
    void testCargarMenuRepetido( )
    {
        File repetidos = new File( "./data/menu_repetido.txt" );
        assertThrows( ProductoRepetidoException.class, ( ) -> {
            restaurante.cargarInformacionRestaurante( archivoIngredientes, repetidos, archivoCombos );
        } );
    }

    @Test
    void testCargarCombosRepetidos( )
    {
        File repetidos = new File( "./data/combos_repetidos.txt" );
        assertThrows( ProductoRepetidoException.class, ( ) -> {
            restaurante.cargarInformacionRestaurante( archivoIngredientes, archivoMenu, repetidos );
        } );
    }

    @Test
    void testCargarComboProductoFaltante( )
    {
        File faltante = new File( "./data/combos_faltante.txt" );
        assertThrows( ProductoFaltanteException.class, ( ) -> {
            restaurante.cargarInformacionRestaurante( archivoIngredientes, archivoMenu, faltante );
        } );
    }

    @Test
    void testIniciarYCerrarPedido( ) throws Exception
    {
        assertNull( restaurante.getPedidoEnCurso( ), "No debería haber un pedido en curso al inicio." );
        assertEquals( 0, restaurante.getPedidos( ).size( ), "La lista de pedidos cerrados debería estar vacía." );

        restaurante.iniciarPedido( "Carlos", "Calle 10" );
        assertNotNull( restaurante.getPedidoEnCurso( ), "Debería existir un pedido en curso." );
        assertEquals( "Carlos", restaurante.getPedidoEnCurso( ).getNombreCliente( ) );

        restaurante.getPedidoEnCurso( ).agregarProducto( new ProductoMenu( "corral", 14000 ) );
        restaurante.cerrarYGuardarPedido( );

        assertNull( restaurante.getPedidoEnCurso( ), "El pedido en curso debería ser null después de cerrarlo." );
        assertEquals( 1, restaurante.getPedidos( ).size( ), "El pedido cerrado debería haberse guardado en el historial." );
    }

    @Test
    void testIniciarPedidoYaEnCurso( ) throws Exception
    {
        restaurante.iniciarPedido( "Carlos", "Calle 10" );
        assertThrows( YaHayUnPedidoEnCursoException.class, ( ) -> {
            restaurante.iniciarPedido( "Maria", "Carrera 7" );
        } );
    }

    @Test
    void testCerrarPedidoSinPedidoEnCurso( )
    {
        assertThrows( NoHayPedidoEnCursoException.class, ( ) -> {
            restaurante.cerrarYGuardarPedido( );
        } );
    }
}
