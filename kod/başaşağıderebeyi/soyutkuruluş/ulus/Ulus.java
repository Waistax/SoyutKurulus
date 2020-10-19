/**
 * başaşağıderebeyi.soyutkuruluş.ulus.Ulus.java
 * 0.4 / 19 Eki 2020 / 11:48:12
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.soyutkuruluş.ulus;

import static başaşağıderebeyi.soyutkuruluş.dünya.Kaynak.*;

import başaşağıderebeyi.soyutkuruluş.dünya.*;
import başaşağıderebeyi.soyutkuruluş.zeka.*;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Ulus {
	public final Dünya dünya;
	public final float[] envanter;
	public final List<Şehir> şehirler;
	public final List<Yol> yollar;
	public final List<Takas> takaslar;
	public final Color renk;

	public Zeka zeka;
	public float gümüş;

	public Ulus(final Dünya dünya, final Color renk) {
		this.dünya = dünya;
		envanter = new float[DEĞERLER.length];
		şehirler = new ArrayList<>();
		yollar = new ArrayList<>();
		takaslar = new ArrayList<>();
		this.renk = renk;
		dünya.uluslar.add(this);
	}
	
	public float durum(final Kaynak kaynak) {
		if (kaynak == null)
			return gümüş;
		return envanter[kaynak.sıra];
	}
	
	public boolean dene(final Kaynak kaynak, final float miktar) {
		return durum(kaynak) >= miktar;
	}
	
	public void ayarla(final Kaynak kaynak, final float miktar) {
		if (kaynak == null)
			gümüş = miktar;
		else
			envanter[kaynak.sıra] = miktar;
	}
	
	public void ekle(final Kaynak kaynak, final float miktar) {
		ayarla(kaynak, durum(kaynak) + miktar);
	}
	
	public void sıfırla(final Kaynak kaynak) {
		ayarla(kaynak, 0.0F);
	}
	
	public void sıfırla() {
		for (final Kaynak kaynak : DEĞERLER)
			sıfırla(kaynak);
		gümüş = 0.0F;
	}
}
