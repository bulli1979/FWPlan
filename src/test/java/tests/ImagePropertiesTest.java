package tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import biz.wgc.fwplan.constants.IconProperties;
import biz.wgc.fwplan.data.EditIcon;

public class ImagePropertiesTest {
	@Test
	public void testProps() {
		IconProperties.instance().setIcons();
		int size = IconProperties.getEditIcons().size();
		System.out.println(size);
		assertTrue(size>0);
		for(EditIcon icon :IconProperties.getEditIcons() ) {
			System.out.println(icon.getTitle());
		}
	}
}
