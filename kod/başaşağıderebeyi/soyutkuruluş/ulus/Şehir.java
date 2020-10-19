/**
 * başaşağıderebeyi.soyutkuruluş.ulus.Şehir.java
 * 0.4 / 19 Eki 2020 / 11:48:20
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.soyutkuruluş.ulus;

import başaşağıderebeyi.matematik.*;
import başaşağıderebeyi.soyutkuruluş.dünya.*;

import java.util.*;

public class Şehir {
	public final Ulus ulus;
	public final Vektör2 köşe;
	public final List<Bölge> komşuBölgeler;
	
	public float seviye;
	
	public Şehir(final Ulus ulus, final Vektör2 köşe) {
		this.ulus = ulus;
		this.köşe = köşe;
		komşuBölgeler = new ArrayList<>();
		seviye = 1.0F;
		ulus.şehirler.add(this);
	}
}
