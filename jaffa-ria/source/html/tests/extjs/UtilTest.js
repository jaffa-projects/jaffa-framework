/*
 * UnitTests for the Jaffa.data.Util class.
 * To execute, load this javascript file after loading the Ext and JaffaRIA libraries,
 * and then either invoke the individual test functions or
 * simply invoke the Jaffa.data.UtilTest.testAll() function.
 */

Jaffa.data.UtilTest = {

  /**
   * @unittest
   * UnitTest for the get() method.
   * Invokes the get() method on a test data structure and compares the returned value with the expected value.
   * The results are printed to the console.
   */
  testAll: function() {
    console.debug('testAll()...');
    this.testGet();
    this.testSet();
  },
  
  /**
   * @unittest
   * UnitTest for the get() method.
   * Invokes the get() method on a test data structure and compares the returned value with the expected value.
   * The results are printed to the console.
   */
  testGet: function() {
    console.debug('testGet()...');
    
    // Invokes Jaffa.data.Util.get and compares the returned value with the expected. The results are printed to the console
    var test = function (data, name, expected) {
      var result = Jaffa.data.Util.get(data, name) == expected;
      result = (result ? '' : '*') + result;
      console.debug('get: ', name, ', expected: ', expected, ', result: ', result);
    };

    // a test data structure
    var data = {
      p0: 'v0',
      p1: 'v1',
      p2: {
        p0: 'v20',
        p1: 'v21',
        p2: ['v220', {p0: 'v2210', p1: 'v2211'}]
      },
      p3: new Ext.util.MixedCollection()
    };
    data.p3.addAll(['v30', 'v31', {p0: 'v320', p1: 'v321'}]);

    // Run some tests
    console.debug('data: ', data);
    test(data, 'p0', 'v0');
    test(data, 'p1', 'v1');
    test(data, 'p2.p0', 'v20');
    test(data, 'p2.p1', 'v21');
    test(data, 'p2.p2[0]', 'v220');
    test(data, 'p2.p2[1].p0', 'v2210');
    test(data, 'p2.p2[1].p1', 'v2211');
    test(data, 'p3.itemAt(0)', 'v30');
    test(data, 'p3.itemAt(1)', 'v31');
    test(data, 'p3.itemAt(2).p0', 'v320');
    test(data, 'p3.itemAt(2).p1', 'v321');
  },

  /**
   * @unittest
   * UnitTest for the set() method.
   * Invokes the set() method on a test data structure and then tests if the value was set correctly.
   * The results are printed to the console.
   */
  testSet: function() {
    console.debug('testSet()...');
    
    // Invokes Jaffa.data.Util.set and then test that the value has been correctly applied. The results are printed to the console
    var test = function (data, name, value) {
      Jaffa.data.Util.set(data, name, value);
      var result = eval('data.' + name) == value;
      result = (result ? '' : '*') + result;
      console.debug('set: ', name, ', value: ', value, ', result: ', result);
    }

    // a test data structure
    var data = {
      p0: 'v0',
      p1: 'v1',
      p2: {
        p0: 'v20',
        p1: 'v21',
        p2: ['v220', {p0: 'v2210', p1: 'v2211'}]
      },
      p3: new Ext.util.MixedCollection()
    };
    data.p3.addAll(['v30', 'v31', {p0: 'v320', p1: 'v321'}]);


    // Run some tests on existing elements
    console.debug('original data: ', data);
    test(data, 'p0', 'nv0');
    test(data, 'p1', 'nv1');
    test(data, 'p2.p0', 'nv20');
    test(data, 'p2.p1', 'nv21');
    test(data, 'p2.p2[0]', 'nv220');
    test(data, 'p2.p2[1].p0', 'nv2210');
    test(data, 'p2.p2[1].p1', 'nv2211');
    test(data, 'p3.itemAt(0)', 'nv30');
    test(data, 'p3.itemAt(1)', 'nv31');
    test(data, 'p3.itemAt(2).p0', 'nv320');
    test(data, 'p3.itemAt(2).p1', 'nv321');

    // create new elements
    test(data, 'p4', 'nv4');
    test(data, 'p5[0]', 'nv50');
    test(data, 'p5[1].p0', 'nv510');
    test(data, 'p5[1].p1', 'nv511');
    test(data, 'p5[2][0]', 'nv520');
    test(data, 'p2.p3', 'nv23');
    test(data, 'p2.p2[2]', 'nv222');
    test(data, 'p2.p2[1].p2', 'nv2212');
    test(data, 'p6.itemAt(0)', 'nv60');
    test(data, 'p6.itemAt(1)[0]', 'nv610');
    test(data, 'p6.itemAt(1)[1]', 'nv611');
    test(data, 'p6.itemAt(1)[0]', 'nv610');
    test(data, 'p6.itemAt(2).itemAt(0).p0', 'nv6200');
    test(data, 'p6.itemAt(2).itemAt(0).p1', 'nv6201');
    console.debug('current data: ', data);
  }
};
