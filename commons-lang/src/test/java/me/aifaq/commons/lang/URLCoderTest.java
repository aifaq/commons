package me.aifaq.commons.lang;

import org.junit.Test;

import java.net.URLDecoder;

/**
 * @author Wang Wei
 * @since 15:31 2017/6/20
 */
public class URLCoderTest {

	@Test
	public void testDecode() throws Exception {
		System.out.println(URLDecoder
				.decode("%3C%3Fxml+version%3D%221.0%22+encoding%3D%22GBK%22%3F%3E%3CGopayAPIResp%3E%3Cversion%3E1.1%3C%2Fversion%3E%3CtranCode%3E4025%3C%2FtranCode%3E%3CcustomerId%3E0000002642%3C%2FcustomerId%3E%3CmerOrderNum%3EC11170620191151277009002%3C%2FmerOrderNum%3E%3CmerURL%3Ehttp%3A%2F%2Fweops.168zhibo.cn%2Fgm%2Fgopay_out%2Fpay_notify%3C%2FmerURL%3E%3CtranAmt%3E0.14%3C%2FtranAmt%3E%3CrecvBankAcctName%3E%CD%F5%CA%BD%D5%BE%3C%2FrecvBankAcctName%3E%3CrecvBankProvince%3E%D5%E3%BD%AD%3C%2FrecvBankProvince%3E%3CrecvBankCity%3E%BA%BC%D6%DD%3C%2FrecvBankCity%3E%3CrecvBankName%3E%D6%D0%B9%FA%BD%A8%C9%E8%D2%F8%D0%D0%3C%2FrecvBankName%3E%3CrecvBankBranchName%3E%D6%A7%D0%D0%3C%2FrecvBankBranchName%3E%3CrecvBankAcctNum%3E6227001541000022975%3C%2FrecvBankAcctNum%3E%3CcorpPersonFlag%3E1%3C%2FcorpPersonFlag%3E%3CtranDateTime%3E20170620141229%3C%2FtranDateTime%3E%3Cdescription%3E--%3C%2Fdescription%3E%3CrespCode%3E8%3C%2FrespCode%3E%3CmsgExt%3E%B8%B6%BF%EE%B3%C9%B9%A6%3C%2FmsgExt%3E%3CorderId%3E2017062003570356%3C%2ForderId%3E%3CfeeAmt%3E2%3C%2FfeeAmt%3E%3CtotalAmount%3E2.14%3C%2FtotalAmount%3E%3CSignValue%3E6e6218d6768dda6b3adf4ac0cfcee88d%3C%2FSignValue%3E%3C%2FGopayAPIResp%3E",
						"gbk"));
	}
}
