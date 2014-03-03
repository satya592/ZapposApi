ZapposApi
=========

Please take the Branch ZapposChallenge

Thumb Rules for performance:
Minimise the IO operations -> Acheived by saving the page ranges(Min,Max).
Minimis processing of Char/Bytes individually -> Acheived

In finding the possible combinations (Best matches) of N products in a given budget. 
I divided the porducts into 4 different ranges with help of facets.
Used Binary search within each "facet bucket" and cached the results for speedier searching.


Required: json-smart jar file to read json data.

