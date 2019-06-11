package br.com.alex.repository.exception

import retrofit2.Response

class RequestServerException(
    response: Response<*>
) : RepositoryException(response.code().toString() + ", " + response.message())