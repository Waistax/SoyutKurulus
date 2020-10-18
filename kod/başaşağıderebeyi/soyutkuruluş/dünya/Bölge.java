/**
 * başaşağıderebeyi.soyutkuruluş.dünya.Bölge.java
 * 0.2 / 18 Eki 2020 / 09:23:44
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.soyutkuruluş.dünya;

import başaşağıderebeyi.matematik.*;

public class Bölge {
	public static final int KENAR_SAYISI = 6;
	public static final float KENAR_UZUNLUĞU = 100.0F;
	public static final double KENAR_AÇISI = Math.PI * 2.0D / KENAR_SAYISI;
	public static final float KENAR_SİNÜS = (float)Math.sin(KENAR_AÇISI) * KENAR_UZUNLUĞU;
	public static final float KENAR_KOSİNÜS = (float)Math.cos(KENAR_AÇISI) * KENAR_UZUNLUĞU;
	public static final Vektör2[] KÖŞELER = {
			new Vektör2(0.0F, -KENAR_UZUNLUĞU),
			new Vektör2(KENAR_SİNÜS, -KENAR_KOSİNÜS),
			new Vektör2(KENAR_SİNÜS, KENAR_KOSİNÜS),
			new Vektör2(0.0F, KENAR_UZUNLUĞU),
			new Vektör2(-KENAR_SİNÜS, KENAR_KOSİNÜS),
			new Vektör2(-KENAR_SİNÜS, -KENAR_KOSİNÜS)
	};
	
	public final Dünya dünya;
	public final Vektör2 merkez;
	public final Vektör2[] köşeler;
	public final Kenar[] kenarlar;
	
	public Bölge(final Dünya dünya, final Vektör2 merkez) {
		this.dünya = dünya;
		this.merkez = merkez;
		köşeler = new Vektör2[KENAR_SAYISI];
		kenarlar = new Kenar[KENAR_SAYISI];
		for (int i = 0; i < KENAR_SAYISI; i++)
			köşeler[i] = new Vektör2(merkez);
		for (int i = 0; i < KENAR_SAYISI; i++) {
			köşeler[i].topla(KÖŞELER[i]);
			kenarlar[i] = new Kenar(this, köşeler[i], köşeler[i < KENAR_SAYISI - 1 ? i + 1 : 0]);
		}
	}
}
