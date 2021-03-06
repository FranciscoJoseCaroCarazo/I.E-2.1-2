package main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import generated.DatosArtic;
import generated.ObjectFactory;
import generated.Ventas;
import generated.Ventas.Venta;
import generated.VentasType;

public class Main {

	public static void main(String[] args) {

		visualizarxml();
		// M�todo para a�adir una venta, recibe el n�mero de venta,
		// las unidades
		// el nombre cliente, la fecha
		// Comprobar que el nm�mero de venta no exista
		// insertarventa(30, "Cliente 2", 10, "16-12-2015");
		// visualizarxml();
		borrarVenta(12);
		visualizarxml();
		modVenta(11, 29, "08-10-2020");
		visualizarxml();
		modStock(33);
		visualizarxml();
		
	}

	////////////////////////////////////////
	public static void visualizarxml() {

		System.out.println("------------------------------ ");
		System.out.println("-------VISUALIZAR XML--------- ");
		System.out.println("------------------------------ ");
		try {
			// JAXBContext jaxbContext = JAXBContext.newInstance("datosclases");
			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);

			// Crear un objeto de tipo Unmarshaller para convertir datos XML en
			// un �rbol de objetos Java
			Unmarshaller u = jaxbContext.createUnmarshaller();

			// La clase JAXBElement representa a un elemento de un documento XML
			// en este caso a un elemento del documento ventasarticulos.xml
			JAXBElement jaxbElement = (JAXBElement) u.unmarshal(new FileInputStream("./ventasarticulos.xml"));

			// Visualizo el documento
			Marshaller m = jaxbContext.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			m.marshal(jaxbElement, System.out);

			// Si queremos operar con el documento obtenemos los
			// objetos del jaxbElement
			// El m�todo getValue() retorna el modelo de contenido (content
			// model) y el valor de los atributos del elemento

			VentasType miventa = (VentasType) jaxbElement.getValue();

			// Obtenemos una instancia para obtener todas las ventas
			Ventas vent = miventa.getVentas();

			// Guardamos las ventas en la lista
			List listaVentas = new ArrayList();
			listaVentas = vent.getVenta();

			System.out.println("---------------------------- ");
			System.out.println("---VISUALIZAR LOS OBJETOS--- ");
			System.out.println("---------------------------- ");
			// Datos del art�culo
			DatosArtic miartic = (DatosArtic) miventa.getArticulo();

			System.out.println("Nombre art: " + miartic.getDenominacion());
			System.out.println("Codigo art: " + miartic.getCodigo());
			System.out.println("Stock art: " + miartic.getCodigo());
			System.out.println("Ventas  del art�culo , hay: " + listaVentas.size());

			for (int i = 0; i < listaVentas.size(); i++) {
				Ventas.Venta ve = (Venta) listaVentas.get(i);
				System.out.println("N�mero de venta: " + ve.getNumventa() + ". Nombre cliente: " + ve.getNombrecliente()
						+ ", unidades: " + ve.getUnidades() + ", fecha: " + ve.getFecha());
			}

		} catch (JAXBException je) {
			System.out.println(je.getCause());
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}

	}

	/////////////////////////////////////////////////
	private static void insertarventa(int numeventa, String nomcli, int uni, String fecha) {

		System.out.println("---------------------------- ");
		System.out.println("-------A�ADIR VENTA--------- ");
		System.out.println("---------------------------- ");
		try {

			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			Unmarshaller u = jaxbContext.createUnmarshaller();
			JAXBElement jaxbElement = (JAXBElement) u.unmarshal(new FileInputStream("./ventasarticulos.xml"));

			VentasType miventa = (VentasType) jaxbElement.getValue();

			// Obtenemos una instancia para obtener todas las ventas
			Ventas vent = miventa.getVentas();

			// Guardamos las ventas en la lista
			List listaVentas = new ArrayList();
			listaVentas = vent.getVenta();

			// comprobar si existe el n�mero de venta, reccorriendo el arraylist
			int existe = 0; // si no existe, 1 si existe
			for (int i = 0; i < listaVentas.size(); i++) {
				Ventas.Venta ve = (Venta) listaVentas.get(i);
				if (ve.getNumventa().intValue() == numeventa) {
					existe = 1;
					break;
				}
			}

			if (existe == 0) {
				// Crear el objeto Ventas.Venta, y si no existe se a�ade a la
				// lista

				Ventas.Venta ve = new Ventas.Venta();
				ve.setNombrecliente(nomcli);
				ve.setFecha(fecha);
				ve.setUnidades(uni);
				BigInteger nume = BigInteger.valueOf(numeventa);
				ve.setNumventa(nume);

				// a�adimos la venta a la lista

				listaVentas.add(ve);

				// crear el Marshaller, volcar la lista al fichero XML
				Marshaller m = jaxbContext.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				m.marshal(jaxbElement, new FileOutputStream("./ventasarticulos.xml"));

				System.out.println("Venta a�adida: " + numeventa);

			} else

				System.out.println("En n�mero de venta ya existe: " + numeventa);

		} catch (JAXBException je) {
			System.out.println(je.getCause());
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}

	}

	/**
	 * M�todo que nos permite Borrar la venta que queramos
	 * @param numeventa
	 * @return
	 */
	private static boolean borrarVenta(int numeventa) {

		System.out.println("---------------------------- ");
		System.out.println("-------BORRAR VENTA--------- ");
		System.out.println("---------------------------- ");
		System.out.println("");
		try {

			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			Unmarshaller u = jaxbContext.createUnmarshaller();
			JAXBElement jaxbElement = (JAXBElement) u.unmarshal(new FileInputStream("./ventasarticulos.xml"));

			VentasType miventa = (VentasType) jaxbElement.getValue();

			// Obtenemos una instancia para obtener todas las ventas
			Ventas vent = miventa.getVentas();

			// Guardamos las ventas en la lista
			List listaVentas = new ArrayList();
			listaVentas = vent.getVenta();

			// comprobar si existe el n�mero de venta, reccorriendo el arraylist
			int existe = 0; // si no existe, 1 si existe
			for (int i = 0; i < listaVentas.size(); i++) {
				Ventas.Venta ve = (Venta) listaVentas.get(i);
				if (ve.getNumventa().intValue() == numeventa) {
					existe = 1;
					listaVentas.remove(i);
					break;
				}
			}

			//Si existe = 1 entonces se cargan los datos en el archivo
			if (existe == 1) {
				
				Marshaller m = jaxbContext.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				m.marshal(jaxbElement, new FileOutputStream("./ventasarticulos.xml"));
				
				System.out.println("Venta " + numeventa + " ha sido eliminada con exito");
				return true;
			} else {
				System.out.println("Venta " + numeventa + " no existe");
				return false;
			}

		} catch (JAXBException je) {
			System.out.println(je.getCause());
			return false;
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
			return false;
		}

	}
	
	/**
	 * Metodo que nos permite modificar las unidades vendidas y
	 * la fecha de la compra.
	 * @param numeventa
	 * @param Newunidades
	 * @param Newfecha
	 * @return
	 */
	private static boolean modVenta(int numeventa, int Newunidades, String Newfecha) {

		System.out.println("---------------------------- ");
		System.out.println("---------MOD VENTAS--------- ");
		System.out.println("---------------------------- ");
		System.out.println("");
		try {

			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			Unmarshaller u = jaxbContext.createUnmarshaller();
			JAXBElement jaxbElement = (JAXBElement) u.unmarshal(new FileInputStream("./ventasarticulos.xml"));

			VentasType miventa = (VentasType) jaxbElement.getValue();

			// Obtenemos una instancia para obtener todas las ventas
			Ventas vent = miventa.getVentas();

			// Guardamos las ventas en la lista
			List listaVentas = new ArrayList();
			listaVentas = vent.getVenta();

			// comprobar si existe el n�mero de venta, reccorriendo el arraylist
			int existe = 0; // si no existe, 1 si existe
			for (int i = 0; i < listaVentas.size(); i++) {
				Ventas.Venta ve = (Venta) listaVentas.get(i);
				if (ve.getNumventa().intValue() == numeventa) {
					existe = 1;
					ve.setUnidades(Newunidades);
					ve.setFecha(Newfecha);
					break;
				}
			}

			//Si existe = 1 entonces se cargan los datos en el archivo
			if (existe == 1) {
				
				Marshaller m = jaxbContext.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				m.marshal(jaxbElement, new FileOutputStream("./ventasarticulos.xml"));

				System.out.println("Venta " + numeventa + " ha sido modificado con exito con exito");
				return true;
			} else {
				System.out.println("Venta " + numeventa + " no existe");
				return false;
			}

		} catch (JAXBException je) {
			System.out.println(je.getCause());
			return false;
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
			return false;
		}

	}
	
	/**
	 * Metodo que nos permite modificar el Stock del producto a vender
	 * @param Stock
	 * @return
	 */
	private static boolean modStock(int Stock) {

		System.out.println("--------------------------- ");
		System.out.println("---------MOD STOCK--------- ");
		System.out.println("--------------------------- ");
		System.out.println("");
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			Unmarshaller u = jaxbContext.createUnmarshaller();
			JAXBElement jaxbElement = (JAXBElement) u.unmarshal(new FileInputStream("./ventasarticulos.xml"));

			VentasType miventa = (VentasType) jaxbElement.getValue();

			Ventas vent = miventa.getVentas();
			DatosArtic miartic = (DatosArtic) miventa.getArticulo();
			
			BigInteger sumarStock = BigInteger.valueOf(Stock);
			BigInteger nuevoStock=miartic.getStock().add(sumarStock);
			miartic.setStock(nuevoStock);
			
			Marshaller m = jaxbContext.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(jaxbElement, new FileOutputStream("./ventasarticulos.xml"));
			
			System.out.println("El Stock a sido actualizado");
			return true;
			
	
		} catch (JAXBException je) {
			System.out.println(je.getCause());
			return false;
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
			return false;
		}
	}
}
