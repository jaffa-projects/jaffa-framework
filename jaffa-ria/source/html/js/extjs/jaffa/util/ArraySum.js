/** Creates a sum function for an array of objects
 * @param field this is the name of the field in each object occurence that should be totalled
 */
Array.prototype.sum = function(field){
  var sum=0;
  if(field)
    for(var i=0;i<this.length;i++) {
      var f = parseFloat(this[i].data[field]);
      if(!isNaN(f))
          sum+=f;
    }
  else
    for(var i=0;i<this.length;sum+=this[i++]);
  return sum;
} 