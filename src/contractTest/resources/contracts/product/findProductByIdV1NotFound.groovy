package contracts.product

import org.springframework.cloud.contract.spec.Contract

Contract.make {

    request {
        method GET()

        headers {
            accept 'application/json'
        }

        url '/api/v1/products/550e8400-e29b-41d4-a716-446655440000'
    }

    response {
        status 404

        headers {
            contentType 'application/problem+json'
        }

        body([
            instance: fromRequest().path(),
            type: "/errors/not-found",
            title: "Not found",
        ])
    }
}