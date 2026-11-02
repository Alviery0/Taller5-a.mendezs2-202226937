package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ProductoAjustadoTest
{
    private ProductoMenu productoBase;
    private ProductoAjustado productoAjustado;
    private Ingrediente ingrediente1;
    private Ingrediente ingrediente2;

    @BeforeEach
    void setUp( ) throws Exception
    {
        productoBase = new ProductoMenu( "corral", 14000 );
        productoAjustado = new ProductoAjustado( productoBase );
        ingrediente1 = new Ingrediente( "queso mozzarella", 2500 );
        ingrediente2 = new Ingrediente( "tomate", 1000 );
    }

    @AfterEach
    void tearDown( ) throws Exception
    {
        productoBase = null;
        productoAjustado = null;
        ingrediente1 = null;
        ingrediente2 = null;
    }

    @Test
    void testGetNombre( )
    {
        assertEquals( "corral", productoAjustado.getNombre( ), "El nombre del producto ajustado no es el esperado." );
    }

    @Test
    void testGetPrecioSinAjustes( )
    {
        assertEquals( 14000, productoAjustado.getPrecio( ), "El precio sin ingredientes adicionales no es el esperado." );
    }

    @Test
    void testGetPrecioConAjustes( )
    {
        productoAjustado.agregarIngrediente( ingrediente1 );
        productoAjustado.eliminarIngrediente( ingrediente2 );
        assertEquals( 16500, productoAjustado.getPrecio( ), "El precio con ingredientes adicionales no es el esperado." );
    }

    @Test
    void testGenerarTextoFactura( )
    {
        productoAjustado.agregarIngrediente( ingrediente1 );
        productoAjustado.eliminarIngrediente( ingrediente2 );

        String esperado = "corral"
                + "    +queso mozzarella                2500\n"
                + "    -tomate\n"
                + "            16500\n";

        assertEquals( esperado, productoAjustado.generarTextoFactura( ), "El texto de la factura no es el esperado." );
    }
}
