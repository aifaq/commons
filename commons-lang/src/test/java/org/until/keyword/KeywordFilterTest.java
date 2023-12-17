package org.until.keyword;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.until.keyword.dictionary.DartsDictionaryBuilder;

/**
 * <pre>
 *
 * </pre>
 *
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 8:01:05 PM Jun 24, 2016
 */
@Ignore
public class KeywordFilterTest {

	KeywordFilter keywordFilter;

	@Before
	public void before() throws Exception {
		String dicFile = KeywordFilterTest.class.getResource("/forbidden.dic").getFile();
		String phraseFile = KeywordFilterTest.class.getResource("/forbidden.txt").getFile();

		DartsDictionaryBuilder.generateSkipDartsDictionary(dicFile, phraseFile, 1, false);

		this.keywordFilter = new KeywordFilter(dicFile);
	}

	@Test
	public void testFilterText() {
		final String text = "计算机等级証 &   子请到西湖";
		
		System.out.println(keywordFilter.filterText(text));
		System.out.println(keywordFilter.containKeyword(text));
		System.out.println(keywordFilter.getKeyword(text));
	}
}
