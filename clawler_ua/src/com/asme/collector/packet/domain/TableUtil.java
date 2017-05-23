package com.asme.collector.packet.domain;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TableUtil {

	public static List<Domain> loadDomain() {
		final List<Domain> domains = new ArrayList<Domain>();
		readClasspathFileLines("resources/zhada//drizzt_business_domain.tsv", "UTF-8", new Callback(){
			@Override
			public void processLine(String line) {
				if(line == null || line.trim().length() == 0) return ;
				String[] fs = line.split("\t");
				if(fs.length != 2) return;
				Domain d = new Domain();
				d.setCampaignId(fs[0]);
				d.setDomain(fs[1]);
				domains.add(d);
			}
		});
		return domains;
	}

	public static List<Keyword> loadKeyword() {
		final List<Keyword> keywords = new ArrayList<Keyword>();
		readClasspathFileLines("resources/zhada//drizzt_business_keyword.tsv", "UTF-8", new Callback(){
			@Override
			public void processLine(String line) {
				if(line == null || line.trim().length() == 0) return ;
				String[] fs = line.split("\t");
				if(fs.length != 2) return;
				Keyword k = new Keyword();
				k.setCampaignId(fs[0]);
				k.setKeyword(fs[1]);
				keywords.add(k);
			}
		});
		return keywords;
	}

	public static List<KeywordRule> loadKeywordRule() {
		final List<KeywordRule> keywordRules = new ArrayList<KeywordRule>();
		readClasspathFileLines("resources/zhada/drizzt_rule.search.tsv", "UTF-8", new Callback(){
			@Override
			public void processLine(String line) {
				if(line == null || line.trim().length() == 0) return ;
				String[] fs = line.split("\t");
				if(fs.length != 9) return;
				KeywordRule k = new KeywordRule();
				k.setId(fs[0]);
				k.setName(fs[1]);
				k.setHost(fs[2]);
				k.setPatternstr(fs[3]);
				k.setAction(fs[4]);
				k.setType(fs[5]);
				k.setClassify(fs[6]);
				k.setDecode(fs[7]);
				k.setCheckdate(fs[8]);
				keywordRules.add(k);
			}
		});
		return keywordRules;
	}

	private static void readClasspathFileLines(String filepath, String enc, Callback callback) {
		BufferedReader r = null;
		try {
			r = new BufferedReader(new InputStreamReader(new FileInputStream(filepath), enc));
			for (String line = r.readLine(); line != null; callback.processLine(line), line = r.readLine());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (r != null)
				try {
					r.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	private static interface Callback{
		public void processLine(String line);
	}
	
	public static void main(String[] args) {
		TableUtil.loadDomain();
		TableUtil.loadKeyword();
		TableUtil.loadKeywordRule();
	}
}
