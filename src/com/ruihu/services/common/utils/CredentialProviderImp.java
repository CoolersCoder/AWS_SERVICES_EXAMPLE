package com.ruihu.services.common.utils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;

public class CredentialProviderImp implements AWSCredentialsProvider {

	@Override
	public AWSCredentials getCredentials() {
		return new BasicAWSCredentials("*", "*");
	}

	@Override
	public void refresh() {
      //we don't use it right now
	}

}
