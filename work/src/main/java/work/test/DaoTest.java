package work.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.ls.LSInput;

import work.dao.TranslationDao;
import work.dto.Translation;
// Translation Dao Test
public class DaoTest {
	
	TranslationDao dao;
	
	@Before
	public void setUp() {
		dao = new TranslationDao();
	}

	@Test
	public void addTest() {
		Translation tl = new Translation();
		tl.setKeyId(1);
		tl.setLocale("en");
		tl.setValue("I'm a boy");

		dao.addTranslation(tl);
				
	}
	
	@Test
	public void getAllTest() {
		
		List<Translation> list = 
				dao.getAllTranslation();
		
		
	for (Translation translation : list) {
		System.out.println(translation.toString());
	}
		
		
	}
	@Test
	public void getAllTranslatioWithOneKeyTest() {
		List<Translation> list =
				dao.getAllTranslatioWithOneKey(2);
		
		for (Translation translation : list) {
			System.out.println(translation);
		}
	}
	
	@Test
	public void updateTranslationTest() {
		dao.updateTranslation("Who am I?", 1, "en");
	}
}
