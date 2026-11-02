package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class PedidoTest
{
    private Pedido pedido1;
    private ProductoMenu producto1;
    private ProductoMenu producto2;

    @BeforeEach
    void setUp( ) throws Exception
    {
        pedido1 = new Pedido( "Juan Perez", "Calle 123" );
        producto1 = new ProductoMenu( "corral", 14000 );
        producto2 = new ProductoMenu( "papas medianas", 6000 );
        pedido1.agregarProducto( producto1 );
        pedido1.agregarProducto( producto2 );
    }

    @AfterEach
    void tearDown( ) throws Exception
    {
        pedido1 = null;
        producto1 = null;
        producto2 = null;
    }

    @Test
    void testGetIdPedido( )
    {
        Pedido pedido2 = new Pedido( "Ana Gomez", "Carrera 45" );
        assertEquals( pedido1.getIdPedido( ) + 1, pedido2.getIdPedido( ), "El id del pedido no se incrementó correctamente." );
    }

    @Test
    void testGetNombreCliente( )
    {
        assertEquals( "Juan Perez", pedido1.getNombreCliente( ), "El nombre del cliente no es el esperado." );
    }

    @Test
    void testGetPrecioTotalPedido( )
    {
        assertEquals( 23800, pedido1.getPrecioTotalPedido( ), "El precio total del pedido no es el esperado." );
    }

    @Test
    void testGenerarTextoFactura( )
    {
        String esperado = "Cliente: Juan Perez\n"
                + "Dirección: Calle 123\n"
                + "----------------\n"
                + "corral\n"
                + "            14000\n"
                + "papas medianas\n"
                + "            6000\n"
                + "----------------\n"
                + "Precio Neto:  20000\n"
                + "IVA:          3800\n"
                + "Precio Total: 23800\n";

        assertEquals( esperado, pedido1.generarTextoFactura( ), "El texto de la factura del pedido no es el esperado." );
    }

    @Test
    void testGuardarFactura( ) throws Exception
    {
        File carpeta = new File( "./facturas" );
        carpeta.mkdirs( );
        File archivo = new File( "./facturas/factura_prueba.txt" );

        pedido1.guardarFactura( archivo );
        assertTrue( archivo.exists( ), "El archivo de la factura no fue creado." );

        String contenido = Files.readString( archivo.toPath( ) );
        assertEquals( pedido1.generarTextoFactura( ), contenido, "El contenido guardado en el archivo no coincide con la factura." );

        archivo.delete( );
    }

    @Test
    void testGuardarFacturaExcepcion( )
    {
        File archivoInvalido = new File( "./carpeta_inexistente_123/factura.txt" );
        assertThrows( FileNotFoundException.class, ( ) -> {
            pedido1.guardarFactura( archivoInvalido );
        } );
    }
}
