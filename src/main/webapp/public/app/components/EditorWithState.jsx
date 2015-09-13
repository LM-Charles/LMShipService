/**
 * Created by desmondz on 7/26/2015.
 */
var EditorWithState = React.createClass({
    getInitialState(){
        return {
            showUpdateOrderStatus: false,
            parameter: {
                orderId: null
            }
        };
    },
    closeUpdateOrderStatus(){
        this.setState({
            showUpdateOrderStatus: false,
            order: null
        });
    },
    updateOrderStatusConfirm(){
        this.closeUpdateOrderStatus();
    },
    openUpdateOrderStatus(){
        this.setState({
            showUpdateOrderStatus: true
        });
    },
    onClickQueryOrder(){
        var result = new AjaxClient().ajaxLoadOrder(this.refs.orderId.getValue());
        console.log(JSON.stringify(result));
        this.setState({
            order: result
        })
    },
    onClickUpdateTracking(sequence){
        var result = new AjaxClient().ajaxSaveTracking(this.state.order.id, this.state.order.shipments[sequence].id, this.refs["tracking" + sequence].getValue());
        this.onClickQueryOrder();
    },
    onClickUpdateDimension(sequence){
        var result = new AjaxClient().ajaxUpdateDimension(this.state.order.id, this.state.order.shipments[sequence].id,
            this.refs["length" + sequence].getValue(),
            this.refs["width" + sequence].getValue(),
            this.refs["height" + sequence].getValue(),
            this.refs["packaging" + sequence].getValue(),
            this.refs["weight" + sequence].getValue()
        );
        this.onClickQueryOrder();
    },
    generateShipmentLines(){
        var rows = [];
        if (this.state.order == null) {
            return [];
        }
        for (var i = 0; i < this.state.order.shipments.length; i++) {
            console.log("GO " + i + " " + this.state.order.shipments[i]);
            var rowHeader = ">> Shipment #" + i;
            rows.push(
                <Row>
                    <Col mdOffset="1" md="11">
                        <div>
                            <Panel collapsible header={rowHeader} eventKey="1">
                        <textarea rows="15" value={JSON.stringify(this.state.order.shipments[i], null, 4)}>
                        </textarea>
                                <Input label='Update Tracking Number'>
                                    <Input type='text' ref={'tracking' + i} placeholder='Enter Tracking Number'/>
                                    <Button onClick={this.onClickUpdateTracking.bind(null, i)}>Update Tracking</Button>
                                </Input>
                                <Input label='Update Dimension'>
                                    <Row>
                                        <Col md="4"><Input ref={'length' + i} type='text' placeholder='Length'/></Col>
                                        <Col md="4"><Input ref={'width' + i} type='text' placeholder='Width'/></Col>
                                        <Col md="4"><Input ref={'height' + i} type='text' placeholder='Height'/></Col>
                                    </Row>
                                    <Row>
                                        <Col md="12">
                                            <Input ref={'packaging' + i} type='select' placeholder='Packaging'>
                                                <option value='CUSTOM'>Self Package</option>
                                                <option value='SMALL'>Small Box</option>
                                                <option value='MEDIUM'>Medium Box</option>
                                                <option value='LARGE'>Large Box</option>
                                            </Input>
                                        </Col>
                                        <Col md="12"> <Input ref={'weight' + i} type='text' placeholder='Weight'/></Col>
                                    </Row>
                                    <Button onClick={this.onClickUpdateDimension.bind(null, i)}>Update
                                        Dimension</Button>
                                </Input>
                            </Panel>
                        </div>
                    </Col>
                </Row>
            )
        }
        return rows;
    },
    generateOrder(){
        if (this.state.order == null) {
            return null;
        }
        var orderHeader = ">> Order Details for " + this.state.order.id;

        return (
            <Row>
                <Col md="12">
                    <Panel collapsible header={{orderHeader}}>
                            <textarea value={JSON.stringify(this.state.order, null, 4)} rows="30">
                            </textarea>
                    </Panel>
                </Col>
            </Row>
        )
    },
    render() {
        var queryShipmentButton = (
            <Button bsStyle="primary" onClick={this.onClickQueryOrder}>Query Order</Button>
        );


        return (
            <div className="component-EditorWithState-container">
                <h3>LM Administrative Website</h3>
                <Row>
                    <Col md="6"><Input type='text' label='Username'/></Col>
                    <Col md="6"><Input type='password' label='Password'/></Col>
                </Row>
                <Row>
                    <Col md="12"> <Input type='text' ref='orderId' label='Search for Order by ID'
                                         placeholder='Enter Order ID' buttonAfter={queryShipmentButton}/></Col>
                </Row>
                {this.generateOrder()}
                {this.generateShipmentLines()}
                <Row>
                    <Col md="12">
                        <Panel>
                            <Row>
                                <Col md="2"><Button className="fill">Re-calculate Rate</Button></Col>
                                <Col md="2"><Button className="fill" onClick={this.openUpdateOrderStatus}>Update
                                    Status...</Button></Col>
                            </Row>
                        </Panel>
                    </Col>
                </Row>
                <Modal show={this.state.showUpdateOrderStatus} onHide={this.closeUpdateOrderStatus}>
                    <Modal.Header closeButton>
                        <Modal.Title>Update Order Status</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Input label="New Status" type='select' placeholder="Select status">
                            <option value='PENDING_PICKUP'>Pending Pickup</option>
                            <option value='PROCESSING'>Processing</option>
                            <option value='IN_TRANSIT'>In Transit</option>
                            <option value='EXCEPTION'>Exception</option>
                            <option value='COMPLETE'>Complete</option>
                        </Input>
                        <Input label="Message" type='textarea' placeholder='Describe your change'/>
                    </Modal.Body>
                    <Modal.Footer>
                        <Row>
                            <Col md="6"><Button onClick={this.closeUpdateOrderStatus}>Close</Button></Col>
                            <Col md="6"><Button bsStyle="primary" onClick={this.updateOrderStatusConfirm}>Save</Button></Col>
                        </Row>
                    </Modal.Footer>
                </Modal>
            </div>
        )
    }
});