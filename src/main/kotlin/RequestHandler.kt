

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestStreamHandler
import java.io.InputStream
import java.io.OutputStream

class RequestHandler : RequestStreamHandler {
    override fun handleRequest(input: InputStream?, output: OutputStream?, context: Context?) {
        output.
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}

//class RequestHandler : RequestHandler