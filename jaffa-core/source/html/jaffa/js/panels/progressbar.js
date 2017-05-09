  var progressEnd = 9;		// set to number of progress <span>'s.
  // set to progress bar color
  var progressInterval = 100;	// set to time between updates (milli-seconds)

  var progressAt = progressEnd;
  var progressTimer;
  function progress_clear() {
          for (var i = 1; i <= progressEnd; i++)
            document.getElementById('progress'+i).style.visibility = 'hidden';
          progressAt = 0;
  }
  function progress_update() {
          progressAt++;
          if (progressAt > progressEnd) {
            progressAt = 1;
          }

          var element = document.getElementById('progress'+progressAt);
          if (element.style.visibility == "visible")
            element.style.visibility = 'hidden';
          else
            element.style.visibility = "visible";

          progressTimer = setTimeout('progress_update()',progressInterval);
  }
  function progress_stop() {
          clearTimeout(progressTimer);
          progress_clear();
          document.all.progressbar.style.visibility='hidden';
  }
  //progressTimer = setTimeout('progress_update()',progressInterval);		// start progress bar


