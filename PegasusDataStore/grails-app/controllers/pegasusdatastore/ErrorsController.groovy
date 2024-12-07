package pegasusdatastore

import exceptions.ResourceNotFoundException

class ErrorsController {

    def handleResourceNotFoundException(ResourceNotFoundException e) {
        render(status: 404, contentType: 'application/json') {
            message e.message ?: "Resource not found"
        }
    }

}
