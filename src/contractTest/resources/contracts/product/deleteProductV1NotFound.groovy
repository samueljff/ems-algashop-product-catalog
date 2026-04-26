package contracts.product

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method DELETE()
        headers {
            accept 'application/json'
        }
        url("/api/v1/products/00000000-0000-0000-0000-000000000000")
    }
    response {
        status 404
        headers {
            contentType 'application/problem+json'
        }
        body([
            instance: fromRequest().path(),
            type    : "/errors/not-found",
            title   : "Not found",
        ])
    }
}