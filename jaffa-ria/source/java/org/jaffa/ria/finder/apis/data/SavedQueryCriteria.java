/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jaffa.ria.finder.apis.data;
import org.jaffa.components.finder.BooleanCriteriaField;
import org.jaffa.components.finder.FinderTx;
import org.jaffa.components.finder.StringCriteriaField;
import org.jaffa.persistence.Criteria;
import org.jaffa.soa.graph.GraphCriteria;

import org.jaffa.ria.finder.domain.SavedQueryMeta;

/**
 *
 * @author BobbyC
 */
public class SavedQueryCriteria extends GraphCriteria {

  /** Holds value of property queryId. */
  private StringCriteriaField queryId;

  /** Holds value of property userId. */
  private StringCriteriaField userId;

  /** Holds value of property componentRef. */
  private StringCriteriaField componentRef;

  /** Holds value of property contextRef. */
  private StringCriteriaField contextRef;

  /** Holds value of property queryName. */
  private StringCriteriaField queryName;

  /** Holds value of property isDefault. */
  private BooleanCriteriaField isDefault;

  /** Holds value of property criteria. */
  private StringCriteriaField criteria;

  /**
   * @return the queryId
   */
  public StringCriteriaField getQueryId() {
    return queryId;
  }

  /**
   * @param queryId
   *          the queryId to set
   */
  public void setQueryId(StringCriteriaField queryId) {
    this.queryId = queryId;
  }

  /**
   * @return the userId
   */
  public StringCriteriaField getUserId() {
    return userId;
  }

  /**
   * @param userId
   *          the userId to set
   */
  public void setUserId(StringCriteriaField userId) {
    this.userId = userId;
  }

  /**
   * @return the componentRef
   */
  public StringCriteriaField getComponentRef() {
    return componentRef;
  }

  /**
   * @param componentRef
   *          the componentRef to set
   */
  public void setComponentRef(StringCriteriaField componentRef) {
    this.componentRef = componentRef;
  }

  /**
   * @return the contextRef
   */
  public StringCriteriaField getContextRef() {
    return contextRef;
  }

  /**
   * @param contextRef
   *          the contextRef to set
   */
  public void setContextRef(StringCriteriaField contextRef) {
    this.contextRef = contextRef;
  }

  /**
   * @return the queryName
   */
  public StringCriteriaField getQueryName() {
    return queryName;
  }

  /**
   * @param queryName
   *          the queryName to set
   */
  public void setQueryName(StringCriteriaField queryName) {
    this.queryName = queryName;
  }

  /**
   * @return the isDefault
   */
  public BooleanCriteriaField getIsDefault() {
    return isDefault;
  }

  /**
   * @param isDefault
   *          the isDefault to set
   */
  public void setIsDefault(BooleanCriteriaField isDefault) {
    this.isDefault = isDefault;
  }

  /**
   * @return the criteria
   */
  public StringCriteriaField getCriteria() {
    return criteria;
  }

  /**
   * @param criteria
   *          the criteria to set
   */
  public void setCriteria(StringCriteriaField criteria) {
    this.criteria = criteria;
  }

  @Override
  public Criteria returnQueryClause(Criteria c) {
    c = super.returnQueryClause(c);
    c.setTable(SavedQueryMeta.getName());
    FinderTx.addCriteria(getQueryId(), SavedQueryMeta.QUERY_ID, c);
    FinderTx.addCriteria(getUserId(), SavedQueryMeta.USER_ID, c);
    FinderTx.addCriteria(getComponentRef(), SavedQueryMeta.COMPONENT_REF, c);
    FinderTx.addCriteria(getContextRef(), SavedQueryMeta.CONTEXT_REF, c);
    FinderTx.addCriteria(getQueryName(), SavedQueryMeta.QUERY_NAME, c);
    FinderTx.addCriteria(getIsDefault(), SavedQueryMeta.IS_DEFAULT, c);
    FinderTx.addCriteria(getCriteria(), SavedQueryMeta.CRITERIA, c);
    return c;
  }
}
