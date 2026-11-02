package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ComboTest
{
    private Combo combo1;
    private ArrayList<ProductoMenu> items;

    @BeforeEach
    void setUp( ) throws Exception
    {
        items = new ArrayList<ProductoMenu>( );
        items.add( new ProductoMenu( "corral", 14000 ) );
        items.add( new ProductoMenu( "papas medianas", 5500 ) );
        items.add( new ProductoMenu( "gaseosa", 5000 ) );

        combo1 = new Combo( "combo corral", 0.10, items );
    }

    @AfterEach
    void tearDown( ) throws Exception
    {
        items = null;
        combo1 = null;
    }

    @Test
    void testGetNombre( )
    {
        assertEquals( "combo corral", combo1.getNombre( ), "El nombre del combo no es el esperado." );
    }

    @Test
    void testGetPrecio( )
    {
        assertEquals( 22050, combo1.getPrecio( ), "El precio del combo no es el esperado." );
    }

    @Test
    void testGenerarTextoFactura( )
    {
        String esperado = "Combo combo corral\n"
                + " Descuento: 0.1\n"
                + "            22050\n";

        assertEquals( esperado, combo1.generarTextoFactura( ), "El texto de la factura del combo no es el esperado." );
    }
}
