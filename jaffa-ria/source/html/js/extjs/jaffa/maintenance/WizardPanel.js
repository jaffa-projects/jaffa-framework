
/**
 * @class Jaffa.maintenance.WizardPanel
 * @extends Ext.Panel
 * Standard Maintenance Panel used for creating a record with a wizard and switching to update mode.
*/
Jaffa.maintenance.WizardPanel = Ext.extend(Ext.Panel, {

    /**
     * @cfg {String/Number} activeItem
     * A string component id or the numeric index of the component that should be initially activated within the
     * container's layout on render. Related to {@link Ext.layout.ContainerLayout#activeItem}.
     */
    activeItem: 0,

    /**
    * @cfg {Boolean} frame
    * false by default to render with plain 1px square borders. true to render with
    * 9 elements, complete with custom rounded corners (also see {@link Ext.Element#boxWrap}).
    */
    frame: true,

    /**
    * @cfg {String} region the placement of the WizardPanel within the viewport (defaults to 'center')
    */
    region: 'center',

    /**
    * @cfg {Object} controller the mechanism used to query the server (defaults to a new Jaffa.component.CRUDController).
    */
    controller: new Jaffa.component.CRUDController(),

    /**
    * @cfg {String} layout the mechanism used to query the server (defaults to 'form' in update mode and 'card' in create mode ).
    */
    layout: this.updateMode ? 'form' : 'card',

    /**
    * @cfg {Array|Ext.Panel} createCards an array of panels that will be used in the create wizard process,
    * You may use an array of panels or just one panel
    * (defaults to a placeholder panel)
    */
    createCards:[{html: 'CREATE CARD'}],

    /**
    * @cfg {Ext.Panel} updateCard a panel that will be used in update mode and switch to update mode
    * (defaults to a placeholder panel)
    */
    updateCard:{html: 'UPDATE CARD'},

    /**
    * @cfg {Object} defaults the default config setting of the wizard
    * (defaults to an object with xtype of formpanel)
    * Note using an xtype of formpanel is preferrable because it support clientvalidation.
    * clientvalidation is applied to all create cards in the Jaffa.maintenance.WizardPanel.addCard method
    */
    defaults:{xtype: 'form'},

    /**
    * {Boolean} updateMode a flag to determine screen settings (defaults to false).
    */
    updateMode: false,

    /**
    * {Boolean} flag to enable/disable FormPanel's monitorValid {@link Ext.form.FormPanel#monitorValid} property for each card(@Type FormPanel). (defaults to true).
     * (@see Ext.form.FormPanel#monitorValid )
    */
    monitorValid: true,

	previousButtonText: 'Previous',
	
	nextButtonText: 'Next',
	
	saveButtonText: 'Save',
	
	finishButtonText: 'Finish',
	

    /**
     * Removes all components from this WizardPanel. Note Ext.Container.removeAll is in Ext 2.1 API, but the method was somehow lost.
     * @param {Boolean} autoDestroy (optional) True to automatically invoke the removed Component's {@link Ext.Component#destroy} function.
     * Defaults to the value of this Container's {@link #autoDestroy} config.
     * @return {Array} Array of the destroyed components
     */
    removeAll: function(autoDestroy){
        this.initItems();
        var item, rem = [], items = [];
        this.items.each(function(i){
            rem.push(i);
        });
        for (var i = 0, len = rem.length; i < len; ++i){
            item = rem[i];
            this.remove(item, autoDestroy);
            if(item.ownerCt !== this){
                items.push(item);
            }
        }
        return items;
    },

    /**
    *   @method onNextCard
    *   Handles a child card's nextBtn click within a wbar.
    *   The default action is to prevalidate the child form then call {@link Jaffa.maintenance.WizardPanel#nextCard nextCard}
    *   If the card  has a onNextCard method, it will override this method
    *   @param {Ext.Button} btn a flag to destroy the item after removal
    *   @param {Event} e the onclick event object
    */
    onNextCard: function(btn, e){
      var wizard = btn.ownerWizard;
      var ai = wizard.layout.activeItem;
      if (typeof ai.onNextCard === 'function') {
        ai.onNextCard(btn,e);
      }else{
        if (wizard.createCards[ai.cardIndex +1]){
          wizard.controller.prevalidate(function(response){
            wizard.nextCard(response);
          });
        }else{
          console.error('Index Out of Bounds. No card found');
        }
      }
    },

    /**
    *   @method nextCard
    *   calls the next card of the card stack to be active
    *   The default action is to add the next card in the createCard cfg then make it active
    *   If the card  has a onNextCard method, it will override this method
    */
    nextCard: function(response){
      var ai = this.layout.activeItem;
      this.createCards[ai.cardIndex + 1].lastCard = ai;
      var newCard = this.addCard(this.createCards[ai.cardIndex + 1]);
      if (newCard.preload)
        newCard.preload(response);
    },

    /**
    *   @method onPreviousCard
    *    Handles a child card's previousBtn click within a wbar.
    *   calls the active card's previous card to be active
    *   The default action is to activate the previous card and destroy and unregister the current from the controller
    *   If the card has an onPreviousCard method, it will override this method
    */
onPreviousCard: function(btn, e){
      var wizard = btn.ownerWizard;
      var ai = wizard.layout.activeItem;
      if (ai.cardIndex) {
	if (wizard.createCards[ai.cardIndex - 1]) {
	  wizard.layout.setActiveItem(ai.lastCard);
	  wizard.doLayout();
	  if(this.controller && ai.findDataSource) {
	    this.controller.unregisterPanel(ai,ai.findDataSource());
	  }
	  wizard.remove(ai, true);
	}
      }
    },


    /**
    *   @method addCard
    *   Adds a panel to the wizard, gives it clientvalidation listeners and resolves wbar configuration.
    *   @param card {Ext.FormPanel} a formpanel config
    *   The card needs to be a config object with an xtype rather than a constructed ext object to support wbar shortcuts
    *   The clientvalidation handles the disabling of the next and save buttons until the form is valid
    */
    addCard: function(card){
      if (!card.rendered) {
        this.initWizardBar(card);
        card = Ext.ComponentMgr.create(card);
      }
      this.controller.applyPanelFieldsMetaRules(card);
      if(!card.listeners){
        card.listeners = {};
      }
      if (!card.listeners.clientvalidation) {
        card.on('clientvalidation', function(form, isValid){
          if (card.nextBtn) {
            card.nextBtn.setDisabled(!isValid);
          }
          if (card.saveBtn) {
            card.saveBtn.setDisabled(!isValid);
          }
          if (card.finishBtn) {
            card.finishBtn.setDisabled(!isValid);
          }
        });
      }
      if(this.rendered) {
        this.add(card);
        this.layout.setActiveItem(card);
        this.doLayout();
      }else{
        this.items = card;
      }
      return card;
    },

    /**
    *   @method switchToUpdateMode
    *   replaces the active createCard with the updateCard, then loads the saved record
    *   @param pk {String} the primary key of the record
    *   The card needs to be a config object with an xtype rather than a constructed ext object to support wbar
    *   The clientvalidation handles the disabling of the next and save buttons until the form is valid
    */
    switchToUpdateMode: function(){
      this.controller.unregisterPanel(this, typeof this.findDataSource=='function' ? this.findDataSource():null);
      this.removeAll(true);
      this.addCard(this.updateCard);
      this.fireEvent('switchtoupdatemode', this);
    },

   /**
    *   @method onSave
    *   Handles the saveBtn click within a wbar.
    *   @param btn {Ext.Button} the saveBtn
    *   In update mode it calls the onUpdate method
    *   In create mode it calls the switchToUpdateMode method
    */
    onSave: function(btn,e){
        this.controller.save();
    },

   /**
    *   @method onFinish
    *   Handles the finishBtn click within a wbar.
    *   @param btn {Ext.Button} the finishBtn
    *   calls the controller.save() method then closes the window
    */
    onFinish: function(btn,e){
      this.controller.save(function(model, response){
        if (viewport && viewport.closeWindowOk)
          viewport.closeWindowOk();
        else
            window.close();
      });
    },

    /**
    *   @method createWizardButton
    *   resolves wbar shortcut strings into extjs button configs
    *   The available shortcuts are as follows:
    *             '>>'      nextBtn      = calls the wizards Jaffa.maintenance.WizardPanel.onNextCard
    *             '<<'      previousBtn  = calls the wizards Jaffa.maintenance.WizardPanel.onPreviousCard
    *             'save'    saveBtn      = calls the wizards Jaffa.maintenance.WizardPanel.onSave
    *             'finish'  finishBtn    = calls the wizards Jaffa.maintenance.WizardPanel.onFinish
    *   @param wizard {Jaffa.maintenance.WizardPanel} a Wizard Panel
    *   @param card {Object} an Ext.Panel config Object with an xtype
    *   @param btn {String|Ext.Button} a shortcut string or Ext.Button
    *   @return btn the converted button
    */
    createWizardButton: function(wizard, card, btn){
        if(typeof btn === 'string'){
            if(btn == '>>'){
                btn = new Ext.Button({
                  itemId: 'nextBtn',
                  text: this.nextButtonText,
                  iconCls: 'next',
                  disabled: wizard.monitorValid,
                  ownerWizard: wizard,
                  handler: function(btn,e){
                    if(typeof card.onNextCard == 'function'){
                      card.onNextCard(btn, e);
                    }else if(typeof wizard.onNextCard== 'function'){
                      wizard.onNextCard(btn,e);
                    }
                  }
                });
                card.nextBtn = btn;
                card.monitorValid = wizard.monitorValid;
            }else if(btn == '<<'){
                btn = new Ext.Button({
                  itemId: 'previousBtn',
                  text: this.previousButtonText,
                  iconCls: 'previous',
                  ownerWizard: wizard,
                  handler: function(btn,e){
                    if(typeof card.onPreviousCard == 'function'){
                      card.onPreviousCard(btn, e);
                    }else if(typeof wizard.onPreviousCard == 'function'){
                      wizard.onPreviousCard(btn,e);
                    }
                  }
                });
                card.previousBtn = btn;
            }else if(btn == 'save'){
                btn = new Ext.Button({
                  itemId: 'saveBtn',
                  text: this.saveButtonText,
                  iconCls: 'save',
                  disabled: wizard.monitorValid,
                  ownerWizard: wizard,
                  handler: function(btn,e){
                      wizard.onSave(btn, e);
                  }
                });
                card.saveBtn = btn;
                card.monitorValid = wizard.monitorValid;
            }else if(btn == 'finish'){
                btn = new Ext.Button({
                  itemId: 'finishBtn',
                  text: this.finishButtonText,
                  iconCls: 'finish',
                  disabled: wizard.monitorValid,
                  ownerWizard: wizard,
                  handler: function(btn,e){
                      wizard.onFinish(btn, e);
                  }
                });
                card.finishBtn = btn;
                card.monitorValid = wizard.monitorValid;
            }
        }
        return btn;
    },

   /**
    *   @method initWizardBar
    *   initializes the wizard bar for a child card.
    *   @param card {Object} the Ext.FormPanel config object
    */
    initWizardBar: function(card){
        if (card.wbar) {
          card.bbar = [];
          Ext.each(card.wbar, function(btn){
            card.bbar.push(this.createWizardButton(this, card, btn));
          }, this);
        }
    },

    /**
    *   @method initComponent
    *   in update mode the updateCard is added.
    *   in create mode the first createCards item is added.
    */
    initComponent: function(){
      if (this.updateMode) {
        this.addCard(this.updateCard);
      }else if(this.createCards.length) {
          for(var i=0;i<this.createCards.length;i++){
            this.createCards[i].cardIndex = i;
          }
          this.addCard(this.createCards[0]);
      }else if(typeof this.createCard === 'object'){
        this.createCards.cardIndex = 0;
        this.addCard(this.createCards);
      }
      this.addEvents('switchtoupdatemode', 'afterupdate');
      Jaffa.maintenance.WizardPanel.superclass.initComponent.call(this);
    },
    onRender: function(ct, position){
     this.controller.applyPanelFieldsMetaRules(this);
     this.controller.loadMaskComponent=this.getEl();
     Jaffa.maintenance.WizardPanel.superclass.onRender.call(this, ct, position);
  }
});

Ext.reg('maintenancewizardpanel', Jaffa.maintenance.WizardPanel);

