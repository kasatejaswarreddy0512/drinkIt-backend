package com.ktsr.drinkIt.controller;

import com.ktsr.drinkIt.DTO.InvoiceDto;
import com.ktsr.drinkIt.entity.Invoice;
import com.ktsr.drinkIt.enums.ErrorCode;
import com.ktsr.drinkIt.helper.APIResponse;
import com.ktsr.drinkIt.helper.ResponseWrapper;
import com.ktsr.drinkIt.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoice")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper> findInvoiceById(@PathVariable Long id){
        try {
            boolean exists= invoiceService.existsById(id);
            if(!exists){
                return APIResponse.get(ErrorCode.INVOICE_NOT_FOUND, "Invoice Not Found", HttpStatus.NOT_FOUND);
            }
            Invoice invoice= invoiceService.getInvoiceById(id);
            return APIResponse.get(ErrorCode.SUCCESS, invoice, HttpStatus.OK);

        } catch (Exception e) {
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/order")
    public ResponseEntity<ResponseWrapper> getInvoiceOrderById(@RequestParam Long orderId){
        try {
            Invoice invoice= invoiceService.getInvoiceOrderId(orderId);
            return APIResponse.get(ErrorCode.SUCCESS, invoice, HttpStatus.OK);
        } catch (Exception e) {
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/invoice-number")
    public ResponseEntity<ResponseWrapper> getInvoiceNumber(@RequestParam String invoiceNumber){
        try {
            Invoice invoice= invoiceService.getInvoiceByInvoiceNumber(invoiceNumber);
            return APIResponse.get(ErrorCode.SUCCESS, invoice, HttpStatus.OK);
        } catch (Exception e) {
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper> updateInvoice(@PathVariable Long id, @RequestBody InvoiceDto dto){
        try {
            boolean exists= invoiceService.existsById(id);
            if(!exists){
                return APIResponse.get(ErrorCode.INVOICE_NOT_FOUND, "Invoice Not Found", HttpStatus.NOT_FOUND);
            }
            Invoice invoice=invoiceService.updateInvoice(id, dto);
            return APIResponse.get(ErrorCode.SUCCESS, invoice, HttpStatus.OK);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper> deleteInvoice(@PathVariable Long id){
        try {
            boolean exists= invoiceService.existsById(id);
            if(!exists){
                return APIResponse.get(ErrorCode.INVOICE_NOT_FOUND, "Invoice Not Found", HttpStatus.NOT_FOUND);
            }
            invoiceService.deleteInvoice(id);
            return APIResponse.get(ErrorCode.SUCCESS,"Invoice Deleted Successfully", HttpStatus.OK);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
