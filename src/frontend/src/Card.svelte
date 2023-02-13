<script>
  import { createEventDispatcher } from "svelte";
  import Button from "./Button.svelte";

  export let productId;
  export let productTitle;
  export let productPrice;
  export let productImage;

  const dispatch = createEventDispatcher();

  function addToCart() {
    dispatch("addcart", productId);
  }
</script>

<style>
  .card {
    box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
    text-align: center;
    font-family: arial;
    float: left;
    height: 30rem;
    width: 25%;
    padding: 0 1rem;
    margin: 1.5rem;
  }

  .price {
    color: grey;
    font-size: 22px;
  }

  .product-image {
    width: 100%;
  }
</style>

<div class="card product">
  <img class="product-image" src={productImage} alt="Product" />
  <h1>{productTitle}</h1>
  <slot name="description">
    <p>No description</p>
  </slot>
  <p class="price">
    €
    <span>{productPrice}</span>
  </p>
  <p class="action">
    <Button on:click={addToCart}>Add to Cart</Button>
  </p>
</div>
