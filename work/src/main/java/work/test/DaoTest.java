package work.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.ls.LSInput;

import work.dao.TranslateDao;
import work.dto.Translation;
// Translation Dao Test
public class DaoTest {
	
	TranslateDao dao;
	
	@Before
	public void setUp() {
		dao = new TranslateDao();
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
}
