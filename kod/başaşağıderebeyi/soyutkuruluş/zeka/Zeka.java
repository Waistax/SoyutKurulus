/**
 * başaşağıderebeyi.soyutkuruluş.zeka.Zeka.java
 * 0.7 / 19 Eki 2020 / 16:12:21
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.soyutkuruluş.zeka;

import başaşağıderebeyi.soyutkuruluş.ulus.*;

public abstract class Zeka {
	public final Ulus ulus;
	
	public Zeka(final Ulus ulus) {
		this.ulus = ulus;
	}
	
	public abstract void günlük();
	
	public abstract void aylık();
	
	public abstract void yıllık();
}
