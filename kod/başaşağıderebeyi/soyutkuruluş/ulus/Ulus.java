/**
 * başaşağıderebeyi.soyutkuruluş.ulus.Ulus.java
 * 0.4 / 19 Eki 2020 / 11:48:12
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.soyutkuruluş.ulus;

import başaşağıderebeyi.soyutkuruluş.dünya.*;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Ulus {
	public final Dünya dünya;
	public final int[] envanter;
	public final List<Şehir> şehirler;
	public final List<Yol> yollar;
	public final Color renk;

	public Ulus(final Dünya dünya, final Color renk) {
		this.dünya = dünya;
		envanter = new int[Kaynak.DEĞERLER.length];
		şehirler = new ArrayList<>();
		yollar = new ArrayList<>();
		this.renk = renk;
		dünya.uluslar.add(this);
	}
}
