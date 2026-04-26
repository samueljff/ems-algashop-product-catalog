package contracts.product

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method PUT()

        headers {
            accept 'application/json'
            contentType 'application/json'
        }

        urlPath('/api/v1/products/00000000-0000-0000-0000-000000000000')

        body([
            name        : "Notebook X11",
            brand       : "Deep Diver",
            regularPrice: 1500.00,
            salePrice   : 1000.00,
            enabled     : true,
            categoryId  : "f5ab7a1e-37da-41e1-892b-a1d38275c2f2",
            description : "A Gamer Notebook"
        ])
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