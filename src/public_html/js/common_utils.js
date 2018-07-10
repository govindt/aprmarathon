function submitNewForm(form, url){
	for ( var i=0; i < form.elements.length; i++) {
		if ( (form.elements[i].type == "radio" || form.elements[i].type == "checkbox") && form.elements[i].checked == false ) {
			continue;	
		}
		if ( i != form.elements.length - 1 )
			url += '&';
		if ( form.elements[i].type == "select-multiple" ) {
			var selectedText = "";
			for ( var j = 0; j < form.elements[i].options.length; j++ ) {
				if (form.elements[i].options[j].selected)
					selectedText += form.elements[i].options[j].value + " ";
			}
			url += form.elements[i].name + '=' + escape(selectedText);
		}
		else {	
			url += form.elements[i].name + '=' + escape(form.elements[i].value);
		}
	}
	OpenWin = window.open(url, "CtrlWindow", "toolbar=no,menubar=no,location=no,scrollbars=yes,resizable=yes");
}

function start(page) {
	OpenWin = window.open(page, "CtrlWindow", "toolbar=no,menubar=no,location=no,scrollbars=yes,resizable=yes");
}

function change(form, elementsToChange, myname) {
       var nameOfElement = new String(elementsToChange);
       var changeTo;
	for ( var i=0; i < form.elements.length; i++) {
		if (form.elements[i].type == "select-one")
		{
			if ( form.elements[i].name == myname ) {
				changeTo = form.elements[i].value;
				break;
			}
		}
		else if ( form.elements[i].type == "checkbox" ) 
		{
		  	if ( form.elements[i].name == myname ) {
				changeTo = form.elements[i].checked;
				break;
			}
		}
	}
	for ( var i=0; i < form.elements.length; i++) {
		if (form.elements[i].type == "select-one")
		{
			len=nameOfElement.length;
			if ( form.elements[i].name.substring(0,len) == nameOfElement ) {
				form.elements[i].value = changeTo; 
			}
		}
		else if (form.elements[i].type == "checkbox")
		{
			len=nameOfElement.length;
			if ( form.elements[i].name.substring(0,len) == nameOfElement ) {
				form.elements[i].checked = changeTo; 
			}
		}
	}
}


// Compare two options within a list by VALUES
function compareOptionValues(a, b) 

{ 

  // Radix 10: for numeric values

  // Radix 36: for alphanumeric values

  var sA = parseInt( a.value, 36 );  

  var sB = parseInt( b.value, 36 );  

  return sA - sB;

}



// Compare two options within a list by TEXT
function compareOptionText(a, b) 

{ 

  // Radix 10: for numeric values

  // Radix 36: for alphanumeric values

  var sA = parseInt( a.text, 36 );  

  var sB = parseInt( b.text, 36 );  

  return sA - sB;

}
// Dual list move function

function moveDualList( srcList, destList, moveAll ) 

{

  // Do nothing if nothing is selected

  if (  ( srcList.selectedIndex == -1 ) && ( moveAll == false )   )

  {

    return;

  }
  newDestList = new Array( destList.options.length );
  var len = 0;
  for( len = 0; len < destList.options.length; len++ ) 

  {

    if ( destList.options[ len ] != null )

    {

      newDestList[ len ] = new Option( destList.options[ len ].text, destList.options[ len ].value, destList.options[ len ].defaultSelected, destList.options[ len ].selected );

    }

  }



  for( var i = 0; i < srcList.options.length; i++ ) 

  { 

    if ( srcList.options[i] != null && ( srcList.options[i].selected == true || moveAll ) )

    {

       // Statements to perform if option is selected



       // Incorporate into new list

       newDestList[ len ] = new Option( srcList.options[i].text, srcList.options[i].value, srcList.options[i].defaultSelected, srcList.options[i].selected );

       len++;

    }

  }



  // Sort out the new destination list

  newDestList.sort( compareOptionValues );   // BY VALUES

  // Populate the destination with the items from the new array

  for ( var j = 0; j < newDestList.length; j++ ) 

  {

    if ( newDestList[ j ] != null )

    {

      destList.options[ j ] = newDestList[ j ];

    }

  }



  // Erase source list selected elements

  for( var i = srcList.options.length - 1; i >= 0; i-- ) 

  { 

    if ( srcList.options[i] != null && ( srcList.options[i].selected == true || moveAll ) )

    {

       // Erase Source

       //srcList.options[i].value = "";

       //srcList.options[i].text  = "";

       srcList.options[i]       = null;

    }

  }
}


/***********************************************
* Dolphin Tabs Menu- by JavaScript Kit (www.javascriptkit.com)
* This notice must stay intact for usage
* Visit JavaScript Kit at http://www.javascriptkit.com/ for this script and 100s more
***********************************************/

var dolphintabs={
	subcontainers:[], last_accessed_tab:null,

	revealsubmenu:function(curtabref){
	this.hideallsubs()
	if (this.last_accessed_tab!=null)
		this.last_accessed_tab.className=""
	if (curtabref.getAttribute("rel")) //If there's a sub menu defined for this tab item, show it
	document.getElementById(curtabref.getAttribute("rel")).style.display="block"
	curtabref.className="current"
	this.last_accessed_tab=curtabref
	},

	hideallsubs:function(){
	for (var i=0; i<this.subcontainers.length; i++)
		document.getElementById(this.subcontainers[i]).style.display="none"
	},


	init:function(menuId, selectedIndex){
	var tabItems=document.getElementById(menuId).getElementsByTagName("a")
		for (var i=0; i<tabItems.length; i++){
			if (tabItems[i].getAttribute("rel"))
				this.subcontainers[this.subcontainers.length]=tabItems[i].getAttribute("rel") //store id of submenu div of tab menu item
			if (i==selectedIndex){ //if this tab item should be selected by default
				tabItems[i].className="current"
				this.revealsubmenu(tabItems[i])
			}
		tabItems[i].onmouseover=function(){
		dolphintabs.revealsubmenu(this)
		}
		} //END FOR LOOP
	}

}


