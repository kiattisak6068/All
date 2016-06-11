@extends('layouts.layout')
@section('title')
@stop

@section('body')
<div style="margin-left:5%">
  {!!Form::open(['route'=>'product.store'])!!}
  {!!Form::label('Name: ')!!}
  {!!Form::text('name',null,['placeholder'=>"give a nanme"])!!}
<br>
  {!!Form::label('Price: ')!!}
  {!!Form::text('price',"0",['placeholder'=>"give a price"])!!}

  {!!Form::submit('Create')!!}
  {!!Form::close()!!}
</div>
  @stop
