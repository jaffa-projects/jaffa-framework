/**
 * @class Ext.data.DWRProxy
 * @extends Ext.data.DataProxy
 * An implementation of {@link Ext.data.DataProxy} that uses DWR to read a data object from a remote server.
 * <p>
 * The DWRProxy has been obtained from http://yui-ext.com/forum/showthread.php?t=19529 
 */
Ext.data.DWRProxy = function (f) {
    Ext.data.DWRProxy.superclass.constructor.call(this);
    this.func = f;
};

Ext.extend(Ext.data.DWRProxy, Ext.data.DataProxy, {
    load : function(params, reader, loadCallback, scope, arg) {
        var dataProxy = this;
        dataProxy.fireEvent("beforeload", dataProxy, params);
        var args = [];
        if (params) args[args.length] = params;
        args[args.length] = {
            callback: function(response) {
                dataProxy.fireEvent("load", dataProxy, response, loadCallback);
                var records = reader.read(response);
                loadCallback.call(scope, records, arg, true);
            },
            exceptionHandler: function(response, e) {
                dataProxy.fireEvent("loadexception", dataProxy, response, loadCallback, e);
                loadCallback.call(scope, null, arg, false);
            },
            errorHandler: function(message, ex) {
                /*
                 When an AJAX call is made in IE in the midst of a long-running rendering of a page (for example if a page contains a Tree with a few hundred rows),
                 then that call usually results in the "Failed to read input" error message. The root cause of the error is 'SocketTimeoutException: Read timed out'
                 in DWR's ParseUtil.parsePost() method. The following hack consumes that error message. The default error handler will be invoked for all other errors.
                 An alternative to the following hack would be to increase the 'connectionTimeout' in deploy/jboss-web.deployer/server.xml
                */
                if (message === 'Failed to read input')
                  dwr.engine._debug("Error: " + ex.name + ", " + ex.message, true);
                else
                  dwr.engine._errorHandler(message, ex);
            }
        };
        this.func.apply(this, args);
    }
});
