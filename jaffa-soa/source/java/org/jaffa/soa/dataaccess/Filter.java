package org.jaffa.soa.dataaccess;

import java.util.regex.Pattern;

import org.jaffa.util.StringHelper;

public class Filter {
	
	private String m_rule;
	private String m_rulePrefix;
	private String m_ruleRegex;
	private Pattern m_pattern;
	private boolean m_exclude = false;

	public Filter (String rule) {
		if(rule.startsWith("-")) {
			m_exclude=true;
			rule=rule.substring(1);
		}
		if(rule.startsWith("+")) {
			rule=rule.substring(1);
		}
		this.m_rule = rule;
		int i = rule.indexOf("*");
		if(i<0) {
			m_rulePrefix = rule;
		} else {
			m_rulePrefix = rule.substring(0,i);
			
			m_ruleRegex = StringHelper.replace(m_rule, ".", "\\.");
			m_ruleRegex = StringHelper.replace(m_ruleRegex, "**", "[\\w\\.]+");
			m_ruleRegex = StringHelper.replace(m_ruleRegex, "*", "[\\w]+");
            //if (log.isDebugEnabled())
            //    log.debug("Converted filter '" + m_rules[i] + "' to pattern '" + filter + "'");
			m_pattern = Pattern.compile(m_ruleRegex);
			
			//ruleRegex = rule.replace('.','/.........)
			//matcher = Patern.compile(ruleRegex)
		}
   }

	public boolean matches(String fld) {
		if (m_pattern != null) {
			if(fld.startsWith(m_rulePrefix)) 
				return m_pattern.matcher(fld).matches();
			else
				return false;
		}
		else {
			return m_rule.equals(fld);
		}
	}

	public String getStaticPrefix() {
		return m_rulePrefix;
	}

	public String getRule() {
		return m_rule;
	}

	public boolean isExcluded() {
		return m_exclude;
	}
	
	public boolean isRegEx() {
		return m_pattern!=null;
	}
	
}
