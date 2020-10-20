/**
 * başaşağıderebeyi.soyutkuruluş.işlem.Süreliİşlem.java
 * 0.9 / 20 Eki 2020 / 07:18:38
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.soyutkuruluş.işlem;

import başaşağıderebeyi.soyutkuruluş.dünya.*;

import java.util.*;

public abstract class Süreliİşlem {
	public final Calendar tamamlanmaZamanı;
	
	public Süreliİşlem(final Dünya dünya, final int süreTürü, final int süre) {
		this.tamamlanmaZamanı = (Calendar)dünya.takvim.clone();
		tamamlanmaZamanı.add(süreTürü, süre);
		dünya.işlemler.add(this);
	}
	
	public abstract void tamamlandı();
}
