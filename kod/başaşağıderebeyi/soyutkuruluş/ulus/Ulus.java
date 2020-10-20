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
	public final int[] envanter;
	public final int[] gelir;
	public final List<Şehir> şehirler;
	public final List<Yol> yollar;
	public final List<Takas> takaslar;
	public final Color renk;

	public Zeka zeka;
	public int gümüş;

	public Ulus(final Dünya dünya, final Color renk) {
		this.dünya = dünya;
		envanter = new int[DEĞERLER.length];
		gelir = new int[DEĞERLER.length];
		şehirler = new ArrayList<>();
		yollar = new ArrayList<>();
		takaslar = new ArrayList<>();
		this.renk = renk;
		dünya.uluslar.add(this);
	}
	
	public int durum(final Kaynak kaynak) {
		if (kaynak == null)
			return gümüş;
		return envanter[kaynak.sıra];
	}
	
	public boolean dene(final Kaynak kaynak, final int miktar) {
		return durum(kaynak) >= miktar;
	}
	
	public boolean dene(final Kaynak kaynak) {
		return dene(kaynak, 1);
	}
	
	public void ayarla(final Kaynak kaynak, final int miktar) {
		if (kaynak == null)
			gümüş = miktar;
		else
			envanter[kaynak.sıra] = miktar;
	}
	
	public void ekle(final Kaynak kaynak, final int miktar) {
		ayarla(kaynak, durum(kaynak) + miktar);
	}
	
	public void ekle(final Kaynak kaynak) {
		ekle(kaynak, 1);
	}
	
	public void çıkar(final Kaynak kaynak, final int miktar) {
		ekle(kaynak, -miktar);
	}
	
	public void çıkar(final Kaynak kaynak) {
		çıkar(kaynak, 1);
	}
	
	public void sıfırla(final Kaynak kaynak) {
		ayarla(kaynak, 0);
	}
	
	public void sıfırla() {
		for (final Kaynak kaynak : DEĞERLER)
			sıfırla(kaynak);
		gümüş = 0;
	}
	
	public void geliriHesapla() {
		for (int i = 0; i < DEĞERLER.length; i++)
			gelir[i] = 0;
		for (final Şehir şehir : şehirler)
			for (final Bölge bölge : şehir.köşe.bölgeler)
				gelir[bölge.kaynak.sıra] += bölge.üretim * şehir.seviye;
	}
	
	public int gelir(final Kaynak kaynak) {
		if (kaynak == null)
			return yollar.size();
		return gelir[kaynak.sıra];
	}
}
